package com.example.news.service;

import com.example.news.entity.UserEntity;
import com.example.news.exception.EntityNotFoundException;
import com.example.news.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public List<UserEntity> findAll(Pageable pageable) {
		return userRepository.findAll(pageable).getContent();
	}

	public UserEntity create(UserEntity entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return userRepository.save(entity);
	}

	public UserEntity findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id = %s не найден", id)));
	}

	@Transactional
	public UserEntity update(Long id, UserEntity userEntity) {
		UserEntity existedUser = findById(id);
		existedUser.setName(userEntity.getName());
		existedUser.setPassword(passwordEncoder.encode(userEntity.getPassword()));


		existedUser.getRoles().removeIf(role -> !userEntity.getRoles().contains(role));
		existedUser.getRoles().addAll(userEntity.getRoles());
		existedUser.getRoles().forEach(role -> role.setUser(existedUser));

		return userRepository.save(existedUser);
	}

	public void delete(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}

	public UserEntity findByName(String name) {
		return userRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("User name not found"));
	}
}
