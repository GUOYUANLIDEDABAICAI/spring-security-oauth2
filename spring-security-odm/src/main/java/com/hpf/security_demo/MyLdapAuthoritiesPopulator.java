package com.hpf.security_demo;

import com.hpf.security_demo.dao.PersonDao;
import com.hpf.security_demo.entity.Person;
import com.hpf.security_demo.entity.Role;
import com.hpf.security_demo.entity.User;
import com.hpf.security_demo.repository.RoleRepository;
import com.hpf.security_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
//@EnableWebSecurity
public class MyLdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

	@Autowired
	PersonDao personDao;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public MyLdapAuthoritiesPopulator(ContextSource contextSource,
			@Value("ou=groups,dc=springframework,dc=org")String groupSearchBase) {
		super(contextSource, groupSearchBase);
	}

	@Override
	protected Set<GrantedAuthority> getAdditionalRoles(
			DirContextOperations user, String username) {
		// 授权集合
		Set<GrantedAuthority> authorites = new HashSet<GrantedAuthority>();
		// 根据username读取用户信息

		//TODO 根据用户信息从DB取得用户角色列表
		System.out.println("权限信息：========================"+username);
		Person person = new Person();
		person.setCn("Ben Alex");
		person.setSn("HanStom");
		//person.setUserPassword("bobspassword");
		List<Person> personList = personDao.getPersonList(person);
		System.out.println("persion list:============="+personList);
		//将角色添加到集合里即可
		User user1 = userRepository.findByName(username);
		List<Role> roles = user1.getRoles();
		if(roles != null)
		{
			for (Role role : roles) {
				GrantedAuthority authority = new LdapAuthority(role.getName(),"");
				authorites.add(authority);
			}
		}
		return authorites;
	}

}
