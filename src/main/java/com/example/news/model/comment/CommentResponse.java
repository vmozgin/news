package com.example.news.model.comment;

import lombok.Data;

@Data
public class CommentResponse {

	private Long id;
	private String text;
	private Long authorId;
}
