package com.example.news.model.news;

import lombok.Data;

@Data
public class NewsCountCommentsResponse {

	private Long id;
	private String title;
	private String description;
	private String author;
	private String category;
	private Integer commentsCount;
}
