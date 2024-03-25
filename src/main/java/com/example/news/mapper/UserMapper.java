package com.example.news.mapper;

import com.example.news.entity.UserEntity;
import com.example.news.model.user.UserListResponse;
import com.example.news.model.user.UserRequest;
import com.example.news.model.user.UserResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	List<UserResponse> userEntityListToUserResponseList(List<UserEntity> source);

	default UserListResponse userListEntityToUserListResponse(List<UserEntity> source) {
		UserListResponse userListResponse = new UserListResponse();
		userListResponse.setAuthors(userEntityListToUserResponseList(source));

		return userListResponse;
	}

	UserResponse userEntityToUserResponse(UserEntity source);

	UserEntity userRequestToUserEntity(UserRequest source);
}