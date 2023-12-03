package com.example.news.mapper;

import com.example.news.entity.NewsEntity;
import com.example.news.model.news.NewsRequest;
import com.example.news.model.news.NewsUpdateRequest;
import com.example.news.service.AuthorsService;
import com.example.news.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper{

	@Autowired
	private AuthorsService authorsService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public NewsEntity newsRequestToNewsEntity(NewsRequest source) {
		NewsEntity entity = new NewsEntity();
		entity.setTitle(source.getTitle());
		entity.setDescription(source.getDescription());
		entity.setCategory(categoryService.findById(source.getCategoryId()));
		entity.setAuthor(authorsService.findById(source.getAuthorId()));

		return entity;
	}

	@Override
	public NewsEntity newsUpdateRequestToNewsEntity(NewsUpdateRequest source) {
		NewsEntity entity = new NewsEntity();
		entity.setTitle(source.getTitle());
		entity.setDescription(source.getDescription());
		entity.setCategory(categoryService.findById(source.getCategoryId()));
		return entity;
	}
}
