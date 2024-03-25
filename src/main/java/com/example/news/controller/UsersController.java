package com.example.news.controller;

import com.example.news.entity.UserEntity;
import com.example.news.mapper.UserMapper;
import com.example.news.model.ErrorResponse;
import com.example.news.model.user.UserListResponse;
import com.example.news.model.user.UserRequest;
import com.example.news.model.user.UserResponse;
import com.example.news.service.UsersService;
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
@RequestMapping("/api/user")
@Validated
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;
	private final UserMapper userMapper;

	@Operation(
			summary = "Получение списка всех пользователей"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
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
	public ResponseEntity<UserListResponse> findAll(
			@RequestParam Integer pageNumber,
			@Min(value = 1, message = "Значение 'pageSize' должно быть больше 0") @RequestParam Integer pageSize
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return ResponseEntity.ok(userMapper.userListEntityToUserListResponse(usersService.findAll(pageable)));
	}

	@Operation(
			summary = "Добавление пользователя"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
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
	public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
		UserEntity newUser = usersService.create(userMapper.userRequestToUserEntity(userRequest));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userMapper.userEntityToUserResponse(newUser));
	}

	@Operation(
			summary = "Обновление пользователя"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
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
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
		UserEntity userEntity = userMapper.userRequestToUserEntity(request);
		return ResponseEntity.ok(userMapper.userEntityToUserResponse(usersService.update(id,
				userEntity)));
	}

	@Operation(
			summary = "Удаление пользователя"
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
		usersService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Получение пользователя по id"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200"
			)
	})
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(userMapper.userEntityToUserResponse(usersService.findById(id)));
	}
}
