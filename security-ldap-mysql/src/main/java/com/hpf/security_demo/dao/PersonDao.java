package com.hpf.security_demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;


import com.hpf.security_demo.entity.Person;
import com.hpf.security_demo.mapper.PersonAttributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

/**
 * Description: 此类的作用是使用spring的 LdapTemplate完成对ldap的增删改查的操作
 * Author:Ding Chengyun
 */
@Component
public class PersonDao {

    //注入spring的LdapTemplate，此处在spring的配置文件中需要配置
    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * 添加 一条记录
     * @param person
     */
    public void createOnePerson(Person person) {
        BasicAttribute ba = new BasicAttribute("objectclass");
        ba.add("person"); //此处的person对应的是core.schema文件中的objectClass：person
        Attributes attr = new BasicAttributes();
        attr.put(ba);
        //必填属性，不能为null也不能为空字符串
        attr.put("cn", person.getCn());
        attr.put("sn", person.getSn());

        //可选字段需要判断是否为空，如果为空则不能添加
        if (person.getUserPassword() != null
                && person.getUserPassword().length() > 0) {
            attr.put("userPassword", person.getUserPassword());
        }
        //bind方法即是添加一条记录。
        ldapTemplate.bind(getDn(person.getCn()), null, attr);
    }

    /**
    
    /**
     * 根据dn查询详细信息
     * @param cn
     * @return
     */
    /*public UcenterLdapApplication getPersonDetail(String cn) {
        try {
            //ldapTeplate的lookup方法是根据dn进行查询，此查询的效率超高
            UcenterLdapApplication ua = (UcenterLdapApplication) 
                ldapTemplate.lookup(getDn(cn),
                        new ApplicationAttributeMapper());
            return ua;
        } catch (NameNotFoundException e) {
            return null;
        }
    }*/

    /**
     * 根据自定义的属性值查询person列表
     * @param person
     * @return
     */
    public List<Person> getPersonList(
            Person person) {
        List<Person> list = new ArrayList<Person>();
        //查询过滤条件
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        
        
        if (person.getCn() != null
                && person.getCn().length() > 0) {
            andFilter.and(new EqualsFilter("cn", person.getCn()));
        }
        if (person.getSn() != null
                && person.getSn().length() > 0) {
            andFilter.and(new EqualsFilter("sn", person.getSn()));
        }

        if (person.getUserPassword() != null
                && person.getUserPassword().length() > 0) {
            andFilter.and(new EqualsFilter("userPassword", person.getUserPassword()));
        }
        //search是根据过滤条件进行查询，第一个参数是父节点的dn，可以为空，不为空时查询效率更高
        list = ldapTemplate.search("", andFilter.encode(),
                new PersonAttributeMapper());
        return list;
    }

    /**
     * 删除一条记录，根据dn
     * @param cn
     */
    public void removeOnePerson(String cn) {
        ldapTemplate.unbind(getDn(cn));
    }

    /**
     * 修改操作
     * @param person
     */
    public void updateOnePerson(Person person) {
        if (person == null || person.getCn() == null 
                || person.getCn().length() <= 0) {
            return;
        }
        List<ModificationItem> mList = new ArrayList<ModificationItem>();
        
        mList.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                new BasicAttribute("sn",person.getSn())));
        mList.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                new BasicAttribute("userPassword",person.getUserPassword())));
        
        if (mList.size() > 0) {
            ModificationItem[] mArray = new ModificationItem[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                mArray[i] = mList.get(i);
            }
            //modifyAttributes 方法是修改对象的操作，与rebind（）方法需要区别开
            ldapTemplate.modifyAttributes(this.getDn(person.getCn()), mArray);
        }
    }
    /**
     * 得到dn
     * @param cn
     * @return
     */
    public DistinguishedName getDn(String cn) {
        //得到根目录，也就是配置文件中配置的ldap的根目录
        DistinguishedName newContactDN = new DistinguishedName("dc=springframework,dc=org");
        // 添加cn，即使得该条记录的dn为"cn=cn,根目录",例如"cn=abc,dc=testdc,dc=com"
        newContactDN.add("cn", cn);
        return newContactDN;
    }
}