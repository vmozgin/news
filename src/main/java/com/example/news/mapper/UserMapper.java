package com.example.news.mapper;

import com.example.news.entity.Role;
import com.example.news.entity.RoleType;
import com.example.news.entity.UserEntity;
import com.example.news.model.user.UserListResponse;
import com.example.news.model.user.UserRequest;
import com.example.news.model.user.UserResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	List<UserResponse> userEntityListToUserResponseList(List<UserEntity> source);

	default UserListResponse userListEntityToUserListResponse(List<UserEntity> source) {
		UserListResponse userListResponse = new UserListResponse();
		userListResponse.setAuthors(userEntityListToUserResponseList(source));

		return userListResponse;
	}

	@Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRoleTypes")
	UserResponse userEntityToUserResponse(UserEntity source);

	default UserEntity userRequestToUserEntity(UserRequest source) {
		UserEntity entity = new UserEntity();
		entity.setName(source.getName());
		entity.setPassword(source.getPassword());
		Set<Role> roles =
				source.getRoles().stream()
						.map(Role::from)
						.peek(role -> role.setUser(entity))
						.collect(Collectors.toSet());
		entity.setRoles(roles);

		return entity;
	}

	@Named("rolesToRoleTypes")
	default Set<RoleType> rolesToRoleTypes(Set<Role> roles) {
		return roles.stream()
				.map(Role::getAuthority)
				.collect(Collectors.toSet());
	}
}
