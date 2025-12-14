package com.site1234.member.dto;

import lombok.Data;

@Data
public class Member {
	private int member_id;
	private String email;
	private String password;
	private String nickname;
	private String phone;
	private String createdAt;
	
}
