package com.example.news.mapper;

import com.example.news.entity.CommentEntity;
import com.example.news.model.comment.CommentCreateRequest;
import com.example.news.model.comment.CommentListResponse;
import com.example.news.model.comment.CommentResponse;
import com.example.news.model.comment.CommentUpdateRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

	List<CommentResponse> commentEntityListToCommentResponseList(List<CommentEntity> source);

	default CommentListResponse commentListEntityToCommentListResponse(List<CommentEntity> source) {
		CommentListResponse commentListResponse = new CommentListResponse();
		commentListResponse.setComments(commentEntityListToCommentResponseList(source));

		return commentListResponse;
	}

	@Mapping(target = "authorId", source = "author.id")
	CommentResponse commentEntityToCommentResponse(CommentEntity source);

	@Mapping(target = "news.id", source = "source.newsId")
	@Mapping(target = "author.id", source = "authorId")
	CommentEntity commentCreateRequestToCommentEntity(CommentCreateRequest source, Long authorId);

	@Mapping(target = "id", source = "commentId")
	CommentEntity commentUpdateRequestToCommentEntity(CommentUpdateRequest source, Long commentId);
}
