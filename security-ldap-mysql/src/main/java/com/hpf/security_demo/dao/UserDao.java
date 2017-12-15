package com.hpf.security_demo.dao;

import com.hpf.security_demo.entity.UserBean;
import com.hpf.security_demo.mapper.LdapAttributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import java.util.List;

@Component
public class UserDao {

	@Autowired
	private LdapTemplate ldapTemplate;

	public List UserList() {
		return ldapTemplate.search("", "(objectclass=idm-user)",
				new AttributesMapper() {
					public Object mapFromAttributes(Attributes attrs)
							throws NamingException {
						return attrs.get("idm-oid").get();
					}
				});
	}
	/*
	 * search����
	 */

	public List<UserBean> getUser(String id) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "idm-user"));
		andFilter.and(new EqualsFilter("idm-oid", id));
		return ldapTemplate.search("", andFilter.encode(),
				new LdapAttributeMapper());
	}
	/*
	 * lookup����
	 */
	public UserBean getUserBean(UserBean bean) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("idm-oid", bean.getId());
		return (UserBean) ldapTemplate.lookup(dn, new LdapAttributeMapper());
	}
	/*
	 * ���
	 */
	public void addUser(UserBean bean) {
		Attributes attr = new BasicAttributes();
		attr.put("objectclass", "top");
		attr.put("objectclass", "person");
		attr.put("cn", bean.getId());
		attr.put("sn", bean.getName());
		DistinguishedName dn = new DistinguishedName();

		dn.add("cn", bean.getId());
		//ldapTemplate.bind(1, 2, 3);1:dn 2:DirContextAdapter 3:Attributes
		ldapTemplate.bind(dn, null, attr);
	}

	/*
	 * ɾ��
	 */
	public void delUser(UserBean bean) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("idm-oid", bean.getId());
		ldapTemplate.unbind(dn);
	}
	/*
	 * rebind�޸�
	 */
	public void updateUser(UserBean bean) {
		Attributes attr = new BasicAttributes();
		attr.put("objectclass", "idm-user");
		if (bean.getId() != null)
			attr.put("idm-oid", bean.getId());
		if (bean.getName() != null)
			attr.put("idm-name", bean.getName());
		if (bean.getStaticPwd() != null)
			attr.put("idm-staticPwd", bean.getStaticPwd());
		DistinguishedName dn = new DistinguishedName();
		dn.add("idm-oid", bean.getId());
		ldapTemplate.rebind(dn, null, attr);
	}
	/*
	 * modify�����޸ķ���
	 */

	public void modifyUser(UserBean bean) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("idm-oid", bean.getId());

		// ֻ���޸�һ�����Եķ���
		/*
		 * ModificationItem itm1 = new ModificationItem(
		 * DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("idm-name",
		 * bean.getName())); ldapTemplate.modifyAttributes(dn, new
		 * ModificationItem[] { itm });
		 */

		// �޸Ķ�����Եķ���1
		/*DirContextOperations dic = ldapTemplate.lookupContext(dn);
		// dic.setAttributeValues("objectclass",
		// new String[] { "top", "idm-user" });
		dic.setAttributeValue("idm-name", bean.getName());
		dic.setAttributeValue("idm-staticPwd", bean.getStaticPwd());
		ldapTemplate.modifyAttributes(dic);*/
		
		//�޸Ķ�����Եķ���2
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		context.setAttributeValue("idm-name", bean.getName());
		context.setAttributeValue("idm-staticPwd", bean.getStaticPwd());
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());

	}
}
