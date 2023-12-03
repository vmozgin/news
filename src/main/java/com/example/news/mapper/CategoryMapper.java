package com.example.news.mapper;

import com.example.news.entity.CategoryEntity;
import com.example.news.model.category.CategoryListResponse;
import com.example.news.model.category.CategoryRequest;
import com.example.news.model.category.CategoryResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

	List<CategoryResponse> categoryEntityListToCategoryResponseList(List<CategoryEntity> source);

	default CategoryListResponse categoryListEntityToCategoryListResponse(List<CategoryEntity> source) {
		CategoryListResponse CategoryListResponse = new CategoryListResponse();
		CategoryListResponse.setCategories(categoryEntityListToCategoryResponseList(source));

		return CategoryListResponse;
	}

	CategoryResponse categoryEntityToCategoryResponse(CategoryEntity source);

	CategoryEntity categoryRequestToCategoryEntity(CategoryRequest source);
}
