package com.example.news.repository;

import com.example.news.entity.NewsEntity;
import com.example.news.model.news.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

	static Specification<NewsEntity> withFilter(NewsFilter newsFilter) {
		return Specification.where(byCategory(newsFilter.getCategory()))
				.and(byAuthorName(newsFilter.getAuthorName()));
	}

	static Specification<NewsEntity> byCategory(String category) {
		return (root, query, criteriaBuilder) -> {
			if (category == null) {
				return null;
			}
			return criteriaBuilder.equal(root.get("category").get("name"), category);
		};
	}

	static Specification<NewsEntity> byAuthorName(String authorName) {
		return (root, query, criteriaBuilder) -> {
			if (authorName == null) {
				return null;
			}
			return criteriaBuilder.equal(root.get("author").get("name"), authorName);
		};
	}
}
