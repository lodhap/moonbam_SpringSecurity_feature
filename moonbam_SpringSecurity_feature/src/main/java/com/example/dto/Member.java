package com.example.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

@Alias("MemberDTO")
public class Member {
	private String id;
	private String username;
	private String password;
	private String email;
	private String role;
	
	private String provider;
	private String providerId;
	
	private Timestamp createDate;
	
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(String id, String username, String password, String email, String role, Timestamp createDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.createDate = createDate;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", role="
				+ role + ", createDate=" + createDate + "]";
	}
	
	
	
}
