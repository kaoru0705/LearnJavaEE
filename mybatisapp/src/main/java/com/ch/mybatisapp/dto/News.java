package com.ch.mybatisapp.dto;

import lombok.Data;

@Data
public class News {
	private int newsId;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private int hit;
}
