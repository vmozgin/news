package com.example.news.model.author;

import java.util.List;
import lombok.Data;

@Data
public class AuthorListResponse {

	private List<AuthorResponse> authors;
}
