package com.hpf.security_demo.mapper;

import com.hpf.security_demo.entity.UserBean;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class LdapAttributeMapper implements AttributesMapper{

	public Object mapFromAttributes(Attributes arrs) throws NamingException {
		UserBean bean=new UserBean();
		String id=(String) arrs.get("idm-oid").get();
		if(id!=null)bean.setId(id);
		String name=(String)arrs.get("idm-name").get();
		if(name!=null)bean.setName(name);
		String staticPwd=(String) arrs.get("idm-staticPwd").get();
		if(staticPwd!=null)bean.setStaticPwd(staticPwd);
		return bean;
	}

}
