package com.example.news.controller;

import com.example.news.entity.CommentEntity;
import com.example.news.mapper.CommentMapper;
import com.example.news.model.ErrorResponse;
import com.example.news.model.comment.CommentCreateRequest;
import com.example.news.model.comment.CommentListResponse;
import com.example.news.model.comment.CommentResponse;
import com.example.news.model.comment.CommentUpdateRequest;
import com.example.news.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {

	private final CommentService commentService;
	private final CommentMapper commentMapper;

	@Operation(
			summary = "Получение списка всех комментариев для новости"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
	public ResponseEntity<CommentListResponse> findAllByNewsId(@RequestParam Long newsId ) {
		return ResponseEntity.ok(commentMapper.commentListEntityToCommentListResponse(commentService.findAllByNewsId(newsId)));
	}

	@Operation(
			summary = "Добавление комментария"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					content = {
							@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
	public ResponseEntity<CommentResponse> create(
			@Valid @RequestBody CommentCreateRequest commentCreateRequest
	) {
		CommentEntity newComment = commentService.create(commentMapper.commentCreateRequestToCommentEntity(
				commentCreateRequest));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(commentMapper.commentEntityToCommentResponse(newComment));
	}

	@Operation(
			summary = "Обновление комментария"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
	public ResponseEntity<CommentResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody CommentUpdateRequest request,
			@NotNull @RequestParam Long authorId
	) {
		CommentEntity commentEntity = commentMapper.commentUpdateRequestToCommentEntity(request, id);
		return ResponseEntity.ok(commentMapper.commentEntityToCommentResponse(commentService.update(id, commentEntity, authorId)));
	}

	@Operation(
			summary = "Получение комментария по id"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
	public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(commentMapper.commentEntityToCommentResponse(commentService.findById(id)));
	}

	@Operation(
			summary = "Удаление комментария"
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
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long authorId) {
		commentService.delete(id, authorId);
		return ResponseEntity.ok().build();
	}
}
