package com.example.news.controller;

import com.example.news.entity.CategoryEntity;
import com.example.news.mapper.CategoryMapper;
import com.example.news.model.ErrorResponse;
import com.example.news.model.category.CategoryListResponse;
import com.example.news.model.category.CategoryRequest;
import com.example.news.model.category.CategoryResponse;
import com.example.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
@RequestMapping("/api/category")
@Validated
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryMapper categoryMapper;
	private final CategoryService categoryService;

	@Operation(
			summary = "Получение списка всех категорий"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
					}
			)
	})
	@GetMapping
	public ResponseEntity<CategoryListResponse> findAll(
			@RequestParam Integer pageNumber,
			@Min(value = 1, message = "Значение 'pageSize' должно быть больше 0") @RequestParam Integer pageSize
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return ResponseEntity.ok(categoryMapper.categoryListEntityToCategoryListResponse(categoryService.findAll(pageable)));
	}

	@Operation(
			summary = "Добавление категории"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
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
	public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest) {
		CategoryEntity newCategory = categoryService.create(categoryMapper.categoryRequestToCategoryEntity(categoryRequest));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoryMapper.categoryEntityToCategoryResponse(newCategory));
	}

	@Operation(
			summary = "Обновление категории"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "404",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			)
	})
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
		CategoryEntity categoryEntity = categoryMapper.categoryRequestToCategoryEntity(request);
		return ResponseEntity.ok(categoryMapper.categoryEntityToCategoryResponse(categoryService.update(id, categoryEntity)));
	}

	@Operation(
			summary = "Удаление категории"
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
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Получение категории по id"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
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
	public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryMapper.categoryEntityToCategoryResponse(categoryService.findById(id)));
	}
}
