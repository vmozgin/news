package com.example.news.repository;

import com.example.news.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@EntityGraph(attributePaths = {"roles"})
	Optional<UserEntity> findByName(String name);
}
