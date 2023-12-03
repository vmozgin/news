package com.example.news.model.news;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {

	private String category;
	private String authorName;

	@AssertTrue(message = "category или authorName обязателен")
	private boolean isCategoryOrAuthorExists() {
		return category != null || authorName != null;
	}
}
