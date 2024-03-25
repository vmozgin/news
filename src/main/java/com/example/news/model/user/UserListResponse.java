package com.example.news.model.user;

import java.util.List;
import lombok.Data;

@Data
public class UserListResponse {

	private List<UserResponse> authors;
}
