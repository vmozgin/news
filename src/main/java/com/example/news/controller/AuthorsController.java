package com.example.news.controller;

import com.example.news.entity.AuthorEntity;
import com.example.news.mapper.AuthorMapper;
import com.example.news.model.ErrorResponse;
import com.example.news.model.author.AuthorListResponse;
import com.example.news.model.author.AuthorRequest;
import com.example.news.model.author.AuthorResponse;
import com.example.news.service.AuthorsService;
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
@RequestMapping("/api/author")
@Validated
@RequiredArgsConstructor
public class AuthorsController {

	private final AuthorsService authorsService;
	private final AuthorMapper authorMapper;

	@Operation(
			summary = "Получение списка всех авторов"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = AuthorResponse.class), mediaType = "application/json")
					}
			),
			@ApiResponse(
					responseCode = "404",
					content = {
							@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
					}
			)
	})
	@GetMapping
	public ResponseEntity<AuthorListResponse> findAll(
			@RequestParam Integer pageNumber,
			@Min(value = 1, message = "Значение 'pageSize' должно быть больше 0") @RequestParam Integer pageSize
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return ResponseEntity.ok(authorMapper.authorListEntityToAuthorListResponse(authorsService.findAll(pageable)));
	}

	@Operation(
			summary = "Добавление автора"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = AuthorResponse.class), mediaType = "application/json")
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
	public ResponseEntity<AuthorResponse> create(@RequestBody @Valid AuthorRequest authorRequest) {
		AuthorEntity newAuthor = authorsService.create(authorMapper.authorRequestToAuthorEntity(authorRequest));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(authorMapper.authorEntityToAuthorResponse(newAuthor));
	}

	@Operation(
			summary = "Обновление автора"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = AuthorResponse.class), mediaType = "application/json")
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
	public ResponseEntity<AuthorResponse> update(@PathVariable Long id, @RequestBody @Valid AuthorRequest request) {
		AuthorEntity authorEntity = authorMapper.authorRequestToAuthorEntity(request);
		return ResponseEntity.ok(authorMapper.authorEntityToAuthorResponse(authorsService.update(id, authorEntity)));
	}

	@Operation(
			summary = "Удаление автора"
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
		authorsService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Получение автора по id"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200"
			)
	})
	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(authorMapper.authorEntityToAuthorResponse(authorsService.findById(id)));
	}
}
