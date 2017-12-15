package com.hpf.security_demo.entity;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import javax.persistence.*;

/**
 * 本测试类person对象来自schema文件的core.schema文件
 * objectClass为person，必填属性和可选属性也是根据该对象得到的。
 * Author:Ding Chengyun
 */
@Entry(objectClasses = {"person","top"},base = "ou=people")
@Entity
@Table(name = "person")
public class Person {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Id
    @Column
    private Name dn;

    @Attribute(name = "sn")
    @Column
    //@DnAttribute(value = "sn",index = 0)
    private String sn; //必填属性

    @Column
    @Attribute(name = "cn")
    //@DnAttribute(value = "cn",index = 1)
    private String cn; //必填属性

    @Column
    @Attribute(name = "userPassword")
    //@DnAttribute(value = "uid",index = 2)
    private String userPassword; //可选属性

    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public String getCn() {
        return cn;
    }
    public void setCn(String cn) {
        this.cn = cn;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}