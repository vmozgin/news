package com.example.news.controller;

import com.example.news.entity.NewsEntity;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.ErrorResponse;
import com.example.news.model.news.NewsFilter;
import com.example.news.model.news.NewsListResponse;
import com.example.news.model.news.NewsRequest;
import com.example.news.model.news.NewsResponse;
import com.example.news.model.news.NewsUpdateRequest;
import com.example.news.model.news.NewsWithoutCommentsResponse;
import com.example.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Validated
public class NewsController {

	private final NewsMapper newsMapper;
	private final NewsService newsService;

	@Operation(
			summary = "Получение списка всех новостей"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
					}
			)
	})
	@GetMapping
	public ResponseEntity<NewsListResponse> findAll(
			@RequestParam Integer pageNumber,
			@Min(value = 1, message = "Значение 'pageSize' должно быть больше 0") @RequestParam Integer pageSize
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return ResponseEntity.ok(newsMapper.newsListEntityToNewsListResponse(newsService.findAll(pageable)));
	}

	@Operation(
			summary = "Добавление новости"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = NewsWithoutCommentsResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "404",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			)
	})
	@PostMapping
	public ResponseEntity<NewsWithoutCommentsResponse> create(@Valid @RequestBody NewsRequest newsRequest) {
		NewsEntity newNews = newsService.create(newsMapper.newsRequestToNewsEntity(newsRequest));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(newsMapper.newsEntityToNewsWithoutCommentsResponse(newNews));
	}

	@Operation(
			summary = "Обновление новости"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = NewsWithoutCommentsResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "404",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "403",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			)
	})
	@PutMapping("/{id}")
	public ResponseEntity<NewsWithoutCommentsResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody NewsUpdateRequest newsUpdateRequest,
			@NotNull @RequestParam Long authorId
	) {
		NewsEntity newNews = newsMapper.newsUpdateRequestToNewsEntity(newsUpdateRequest);
		return ResponseEntity.ok(newsMapper.newsEntityToNewsWithoutCommentsResponse(newsService.update(id, newNews, authorId)));
	}

	@Operation(
			summary = "Получение новости по id"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "404",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			)
	})
	@GetMapping("/{id}")
	public ResponseEntity<NewsResponse> findById(
			@PathVariable Long id
	) {
		return ResponseEntity.ok(newsMapper.newsEntityToNewsResponse(newsService.findById(id)));
	}

	@Operation(
			summary = "Удаление новости"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200"
			),
			@ApiResponse(
					responseCode = "404",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "403",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@PathVariable Long id,
			@RequestParam Long authorId
	) {
		newsService.delete(id, authorId);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Получение новостей по фильтрам"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200"
			)
	})
	@PostMapping("/filter")
	public ResponseEntity<NewsListResponse> filterBy(@Valid NewsFilter filter) {
		return ResponseEntity.ok(newsMapper.newsListEntityToNewsListResponse(newsService.filterBy(filter)));
	}
}
