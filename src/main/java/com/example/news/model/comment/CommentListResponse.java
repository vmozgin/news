package com.example.news.model.comment;

import java.util.List;
import lombok.Data;

@Data
public class CommentListResponse {

	private List<CommentResponse> comments;
}
