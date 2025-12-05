package com.ch.model1.dto;

import lombok.Data;

@Data
public class Comment {
	private int commentId;
	private String msg;
	private String reader;
	private String writedate;
	private int readCount;
	private News news;
}
