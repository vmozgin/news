package com.example.news.mapper;

import com.example.news.entity.NewsEntity;
import com.example.news.model.news.NewsCountCommentsResponse;
import com.example.news.model.news.NewsListResponse;
import com.example.news.model.news.NewsRequest;
import com.example.news.model.news.NewsResponse;
import com.example.news.model.news.NewsUpdateRequest;
import com.example.news.model.news.NewsWithoutCommentsResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CommentMapper.class)
public interface NewsMapper {

	default List<NewsCountCommentsResponse> newsEntityListToNewsCountCommentsResponseList(List<NewsEntity> source) {
		return source.stream()
				.map(this::newsEntityToNewsCommentsCountResponse)
				.collect(Collectors.toList());
	}

	default NewsListResponse newsListEntityToNewsListResponse(List<NewsEntity> source) {
		NewsListResponse newsListResponse = new NewsListResponse();
		newsListResponse.setNews(newsEntityListToNewsCountCommentsResponseList(source));

		return newsListResponse;
	}

	@Mapping(target = "author", source = "author.name")
	@Mapping(target = "category", source = "category.name")
	NewsResponse newsEntityToNewsResponse(NewsEntity source);

	default NewsCountCommentsResponse newsEntityToNewsCommentsCountResponse(NewsEntity source) {
		NewsCountCommentsResponse newsCountCommentsResponse = new NewsCountCommentsResponse();
		newsCountCommentsResponse.setId(source.getId());
		newsCountCommentsResponse.setTitle(source.getTitle());
		newsCountCommentsResponse.setDescription(source.getDescription());
		newsCountCommentsResponse.setCategory(source.getCategory().getName());
		newsCountCommentsResponse.setAuthor(source.getAuthor().getName());
		newsCountCommentsResponse.setCommentsCount(source.getComments().size());

		return newsCountCommentsResponse;
	}

	NewsEntity newsRequestToNewsEntity(NewsRequest source);

	NewsEntity newsUpdateRequestToNewsEntity(NewsUpdateRequest source);

	@Mapping(target = "author", source = "author.name")
	@Mapping(target = "category", source = "category.name")
	NewsWithoutCommentsResponse newsEntityToNewsWithoutCommentsResponse(NewsEntity source);
}
