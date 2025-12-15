package com.ch.shop.test.school;

/* 학교에서 공부하는 학생을 표현하기 */
public class Student {
	
	private Bell bell;
	
	// 생성자 주입
	public Student(Bell bell) {
		this.bell = bell;
	}
	
	public void gotoSchool() {
		bell.ding();
		System.out.println("등교해요");
	}
	public void study() {
		bell.ding();
		System.out.println("공부해요");		
	}
	public void rest() {
		bell.ding();
		System.out.println("쉬는 시간을 가져요");
	}
	public void havaLunch() {
		bell.ding();
		System.out.println("점심을 먹어요");
	}
	public void goHome() {
		bell.ding();
		System.out.println("귀가해요");
	}
}
