package com.example.news.service;

import com.example.news.entity.UserEntity;
import com.example.news.exception.EntityNotFoundException;
import com.example.news.repository.UserRepository;
import com.example.news.utils.BeanUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UserRepository userRepository;

	public List<UserEntity> findAll(Pageable pageable) {
		return userRepository.findAll(pageable).getContent();
	}

	public UserEntity create(UserEntity entity) {
		return userRepository.save(entity);
	}

	public UserEntity findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id = %s не найден", id)));
	}

	@Transactional
	public UserEntity update(Long id, UserEntity userEntity) {
		UserEntity existedAuthor = findById(id);
		BeanUtils.copyNonNullProperties(userEntity, existedAuthor);

		return userRepository.save(existedAuthor);
	}

	public void delete(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}
}
