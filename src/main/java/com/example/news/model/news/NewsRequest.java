package com.example.news.model.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewsRequest {

	@NotBlank(message = "Поле 'title' не заполнено")
	@Size(max = 50, message = "Длина заголовка должна быть не более 50 символов")
	private String title;
	@NotBlank(message = "Поле 'description' не заполнено")
	private String description;
	@NotNull(message = "Поле 'categoryId' не заполнено")
	@Positive(message = "Значение 'categoryId' должно быть больше 0")
	private Long categoryId;
}
