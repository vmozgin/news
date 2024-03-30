package com.example.news.model.user;

import com.example.news.entity.RoleType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private Long id;
	private String name;
	private Set<RoleType> roles;
}
