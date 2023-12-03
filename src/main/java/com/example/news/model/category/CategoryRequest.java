package com.example.news.model.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

	@NotBlank(message = "Поле 'name' не заполнено")
	private String name;
}
