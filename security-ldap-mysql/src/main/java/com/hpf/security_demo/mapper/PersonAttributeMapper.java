package com.hpf.security_demo.mapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import com.hpf.security_demo.entity.Person;
import org.springframework.ldap.core.AttributesMapper;

import java.util.Arrays;

/**
 * 这个类的作用是将ldap中的属性转化为实体类的属性值，
 * 在查询信息的时候会用到
 */
public class PersonAttributeMapper implements AttributesMapper{

    @Override
    public Object mapFromAttributes(Attributes attr) throws NamingException {
        Person person = new Person();
        person.setSn((String)attr.get("sn").get());
        person.setCn((String)attr.get("cn").get());
        
        if (attr.get("userPassword") != null) {
            byte[] userPassword = (byte[]) attr.get("userPassword").get();
            person.setUserPassword(Arrays.toString(userPassword));
        }
        return person;
    }

}