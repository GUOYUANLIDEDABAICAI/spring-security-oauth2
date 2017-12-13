package com.hpf.security_demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthority;

public class MyLdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

	public MyLdapAuthoritiesPopulator(ContextSource contextSource,
			String groupSearchBase) {
		super(contextSource, groupSearchBase);
	}
	
	protected Set<GrantedAuthority> getAdditionalRoles(
			DirContextOperations user, String username) {
		// 授权集合
		Set<GrantedAuthority> authorites = new HashSet<GrantedAuthority>();
		// 根据username读取用户信息
		
		//TODO 根据用户信息从DB取得用户角色列表
		
		//将角色添加到集合里即可
		GrantedAuthority authority = new LdapAuthority("","");
		authorites.add(authority);
			
		return authorites;
	}

}
