package com.ch.model1.dto;

public class Member2 {
	/*
	 * 아래의 클래스는 로직을 작성하기 위함이 아니라, 오직 Member2라는 테이블과의 CRUD에
	 * 사용하기 위한 데이터를 담는 목적의 클래스일 뿐이다.. (dummy) DTO라 한다.
	 * 자바 용어가 아니라, 대부분의 어플리케이션 제작 시 데이터를 담는 목적의 객체를 가리키는 범용적 용어
	 * 
	 */
	private int member2Id;
	private String id;
	private String name;
	private String email;
	
	public int getMember2Id() {
		return member2Id;
	}
	public void setMember2Id(int member2Id) {
		this.member2Id = member2Id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
