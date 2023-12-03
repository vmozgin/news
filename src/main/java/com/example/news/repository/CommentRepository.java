package com.example.news.repository;

import com.example.news.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	public List<CommentEntity> findCommentEntitiesByNews_Id(Long id);
}
