package com.hpf.security_demo.entity;

/**
 * 本测试类person对象来自schema文件的core.schema文件
 * objectClass为person，必填属性和可选属性也是根据该对象得到的。
 * Author:Ding Chengyun
 */
public class Person {

    private String sn; //必填属性
    private String cn; //必填属性
    
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