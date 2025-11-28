package com.ch.notice.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegistForm extends JFrame{
	
	// 클래스가 보유한 멤버변수가 객체형일 경우 has a 관계
	JTextField title;		// 제목 입력 textbox
	JTextField writer;
	JTextArea content;		// 내용 입력 박스
	JButton bt;		// 등록버튼
	
	
	public RegistForm() {
		title = new JTextField(30);	// 텍스트 박스의 디자인 길이
		writer = new JTextField(30);
		content = new JTextArea(10, 30);
		bt = new JButton("등록");
		
		// 컴포넌트를 부착하기 전에 레이아웃을 결정짓자. css div로 레이아웃을 적용하는 것과 비슷
		setLayout(new FlowLayout());		// 수평이나 수직으로 흐르는 레이아웃
		
		this.add(title);		// 윈도우인 나의 몸체에 title을 부착
		this.add(writer);
		this.add(content);
		this.add(bt);		// 윈도우인 나의 몸체에 버튼을 부착
		
		this.setSize(400, 300);		// 너비와 높이를 부여
		this.setVisible(true);		// default 가 안 보이므로 보이게
	}
	
	public static void main(String[] args) {
		RegistForm win = new RegistForm();
		
	}
}
