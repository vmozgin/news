package com.example.news.service;

import com.example.news.entity.AuthorEntity;
import com.example.news.exception.EntityNotFoundException;
import com.example.news.repository.AuthorRepository;
import com.example.news.utils.BeanUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorsService {

	private final AuthorRepository authorRepository;

	public List<AuthorEntity> findAll(Pageable pageable) {
		return authorRepository.findAll(pageable).getContent();
	}

	public AuthorEntity create(AuthorEntity entity) {
		return authorRepository.save(entity);
	}

	public AuthorEntity findById(Long id) {
		return authorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Автор с id = %s не найден", id)));
	}

	@Transactional
	public AuthorEntity update(Long id, AuthorEntity authorEntity) {
		AuthorEntity existedAuthor = findById(id);
		BeanUtils.copyNonNullProperties(authorEntity, existedAuthor);

		return authorRepository.save(existedAuthor);
	}

	public void delete(Long id) {
		findById(id);
		authorRepository.deleteById(id);
	}
}
