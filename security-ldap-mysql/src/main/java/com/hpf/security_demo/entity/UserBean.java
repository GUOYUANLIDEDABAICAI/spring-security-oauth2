package com.hpf.security_demo.entity;

public class UserBean {
	private String id;
	private String name;
	private String staticPwd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStaticPwd() {
		return staticPwd;
	}
	public void setStaticPwd(String staticPwd) {
		this.staticPwd = staticPwd;
	}
	
}
