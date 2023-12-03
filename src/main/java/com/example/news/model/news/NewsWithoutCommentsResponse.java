package com.example.news.model.news;

import lombok.Data;

@Data
public class NewsWithoutCommentsResponse {

	private Long id;
	private String title;
	private String description;
	private String author;
	private String category;
}
