package com.example.news.mapper;

import com.example.news.entity.AuthorEntity;
import com.example.news.model.author.AuthorListResponse;
import com.example.news.model.author.AuthorRequest;
import com.example.news.model.author.AuthorResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

	List<AuthorResponse> authorEntityListToAuthorResponseList(List<AuthorEntity> source);

	default AuthorListResponse authorListEntityToAuthorListResponse(List<AuthorEntity> source) {
		AuthorListResponse authorListResponse = new AuthorListResponse();
		authorListResponse.setAuthors(authorEntityListToAuthorResponseList(source));

		return authorListResponse;
	}

	AuthorResponse authorEntityToAuthorResponse(AuthorEntity source);

	AuthorEntity authorRequestToAuthorEntity(AuthorRequest source);
}
