package com.example.news.model.news;

import com.example.news.model.comment.CommentResponse;
import java.util.List;
import lombok.Data;

@Data
public class NewsResponse {

	private Long id;
	private String title;
	private String description;
	private String author;
	private String category;
	private List<CommentResponse> comments;
}
