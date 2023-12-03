package com.example.news.service;

import com.example.news.entity.CategoryEntity;
import com.example.news.exception.EntityNotFoundException;
import com.example.news.repository.CategoryRepository;
import com.example.news.utils.BeanUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<CategoryEntity> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable).getContent();
	}

	public CategoryEntity create(CategoryEntity entity) {
		return categoryRepository.save(entity);
	}

	public CategoryEntity findById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Категория с id = %s не найдена", id)));
	}

	public CategoryEntity update(Long id, CategoryEntity categoryEntity) {
		CategoryEntity existedCategory = findById(id);
		BeanUtils.copyNonNullProperties(categoryEntity, existedCategory);

		return categoryRepository.save(existedCategory);
	}

	public void delete(Long id) {
		findById(id);
		categoryRepository.deleteById(id);
	}
}
