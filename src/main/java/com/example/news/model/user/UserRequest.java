package com.example.news.model.user;

import com.example.news.entity.RoleType;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	@NotBlank(message = "Поле 'name' не заполнено")
	private String name;
	@NotBlank(message = "Поле 'password' не заполнено")
	private String password;
	private List<RoleType> roles;
}
