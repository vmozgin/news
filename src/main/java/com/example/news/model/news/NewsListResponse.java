package com.example.news.model.news;

import java.util.List;
import lombok.Data;

@Data
public class NewsListResponse {

	private List<NewsCountCommentsResponse> news;
}
