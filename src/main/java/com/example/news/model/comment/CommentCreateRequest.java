package com.example.news.model.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CommentCreateRequest {

	@NotNull(message = "Поле 'newsId' не должно быть пустым")
	@Positive(message = "Поле 'newsId' должно быть больше 0")
	private Long newsId;
	@NotBlank(message = "Поле 'text' не должно быть пустым")
	private String text;
	@NotNull(message = "Поле 'authorId' не должно быть пустым")
	@Positive(message = "Поле 'authorId' должно быть больше 0")
	private Long authorId;
}
