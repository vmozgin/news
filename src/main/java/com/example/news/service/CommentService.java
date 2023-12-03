package com.example.news.service;

import com.example.news.aop.CheckCommentPermission;
import com.example.news.entity.CommentEntity;
import com.example.news.entity.NewsEntity;
import com.example.news.exception.EntityNotFoundException;
import com.example.news.repository.CommentRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.utils.BeanUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final NewsRepository newsRepository;
	private final AuthorsService authorsService;

	public List<CommentEntity> findAllByNewsId(Long newsId) {
		validateNewsId(newsId);
		return commentRepository.findCommentEntitiesByNews_Id(newsId);
	}

	public CommentEntity findById(Long id) {
		return commentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Комментарий с id = %s не найден", id)));
	}

	public CommentEntity create(CommentEntity commentEntity) {
		validateNewsId(commentEntity.getNews().getId());
		authorsService.findById(commentEntity.getAuthor().getId());
		return commentRepository.save(commentEntity);
	}

	@Transactional
	@CheckCommentPermission
	public CommentEntity update(Long id, CommentEntity commentEntity, Long authorId) {
		CommentEntity existedComment = findById(id);
		BeanUtils.copyNonNullProperties(commentEntity, existedComment);

		return commentRepository.save(existedComment);
	}

	@CheckCommentPermission
	public void delete(Long id, Long authorId) {
		findById(id);
		commentRepository.deleteById(id);
	}

	private void validateNewsId(Long id) {
		Optional<NewsEntity> news = newsRepository.findById(id);
		if (news.isEmpty())  {
			throw new EntityNotFoundException(String.format("Новость с id = %s не найдена", id));
		}
	}
}
