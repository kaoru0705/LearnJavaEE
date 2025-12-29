package com.ch.shop.dto;

import lombok.Data;

/*
 {
 	"id": "32742776",
 	"email" : "test@naver.com",
 	"name": "홍길동",
 	"nickname": "길동",
 	"profile_image": "https://...",
 	"gender": "M",
 	"age": "20-29",
 	"birthday": "10-01"
 }
 
 */
@Data
public class NaverUser 
{
	private String id;
	private String email;
	private String name;
	private String nickname;
	private String profile_image;
	private String gender;
	private String age;
	private String birthday;
}
