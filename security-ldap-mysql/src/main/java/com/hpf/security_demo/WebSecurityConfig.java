package com.hpf.security_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private ReaderRepository readerRepository;
    @Autowired
    MyLdapAuthoritiesPopulator myLdapAuthoritiesPopulator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/login").permitAll()
               // .antMatchers("/test").access("hasRole('ROLE_MANAGERS')")
                //.antMatchers("/**").access("hasRole('ROLE_DEVELOPERS')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
    }

    //@Autowired
    //CustomUserDetailsService customUserDetailsService;

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.userDetailsService(customUserDetailsService);
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8889/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                //.passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.userDetailsService(customUserDetailsService);
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .ldapAuthoritiesPopulator(myLdapAuthoritiesPopulator)
                .contextSource()
                .url("ldap://localhost:8889/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                //.passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }

}
