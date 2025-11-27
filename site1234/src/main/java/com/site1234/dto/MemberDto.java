package com.site1234.dto;

public class MemberDto {
	int memberId;
	String email;
	String password;
	String phone;
	
	MemberDto(int meberId, String email, String password, String phone) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
	
	int getMemberId() {
		return memberId;
	}
	void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	String getEmail() {
		return email;
	}
	void setEmail(String email) {
		this.email = email;
	}
	
	String getPassword() {
		return password;
	}
	void setPassword(String password) {
		this.password = password;
	}
	
	String getPhone() {
		return phone;
	}
	void setPhone(String phone) {
		this.phone = phone;
	}
	
}
