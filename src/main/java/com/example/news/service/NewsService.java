package com.example.news.service;

import com.example.news.entity.NewsEntity;
import com.example.news.exception.EntityNotFoundException;
import com.example.news.model.news.NewsFilter;
import com.example.news.repository.NewsRepository;
import com.example.news.repository.NewsSpecification;
import com.example.news.utils.BeanUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsService {

	private final NewsRepository newsRepository;

	public List<NewsEntity> findAll(Pageable pageable) {
		return newsRepository.findAll(pageable).getContent();
	}

	public NewsEntity create(NewsEntity entity) {
		return newsRepository.save(entity);
	}

	public NewsEntity findById(Long id) {
		return newsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Новость с id = %s не найдена", id)));
	}

	@Transactional
	public NewsEntity update(Long id, NewsEntity newsEntity) {
		NewsEntity existedNews = findById(id);
		BeanUtils.copyNonNullProperties(newsEntity, existedNews);

		return newsRepository.save(existedNews);
	}

	public void delete(Long id) {
		findById(id);
		newsRepository.deleteById(id);
	}

	public List<NewsEntity> filterBy(NewsFilter filter) {
		return newsRepository.findAll(NewsSpecification.withFilter(filter));
	}
}
