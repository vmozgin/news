package com.example.news.aop;

import com.example.news.exception.AccessDeniedException;
import com.example.news.security.AppUserPrincipal;
import com.example.news.service.CommentService;
import com.example.news.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

	private final CommentService commentService;
	private final NewsService newsService;

	@Before("@annotation(com.example.news.aop.CheckCommentPermission)")
	public void checkCommentPermission() {
		Long commentId = extractIdFromPathVariable();
		Long actualAuthorId = commentService.findById(commentId).getAuthor().getId();

		checkPermission(actualAuthorId);
	}

	@Before("@annotation(com.example.news.aop.CheckNewsPermission)")
	public void checkNewsPermission() {
		Long newsId = extractIdFromPathVariable();
		Long actualAuthorId = newsService.findById(newsId).getAuthor().getId();

		checkPermission(actualAuthorId);
	}

	@Before("@annotation(com.example.news.aop.CheckUserRole)")
	public void checkUserRole() {
		Long requestUserId = extractIdFromPathVariable();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUserPrincipal userDetails = (AppUserPrincipal) authentication.getPrincipal();
		Long currentUserId = userDetails.getId();

		if (hasOnlyRoleUser(authentication)) {
			if (!currentUserId.equals(requestUserId)) {
				throw new AccessDeniedException("Операция недоступна для текущего пользователя");
			}
		}
	}

	private void checkPermission(Long actualAuthorId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUserPrincipal userDetails = (AppUserPrincipal) authentication.getPrincipal();
		Long requestAuthorId = userDetails.getId();

		if (hasOnlyRoleUser(authentication)) {
			if (!actualAuthorId.equals(requestAuthorId)) {
				throw new AccessDeniedException(
						String.format("Действие запрещено, пользователь с id '%s' не является автором", requestAuthorId));
			}
		}
	}

	private Long extractIdFromPathVariable() {
		HttpServletRequest request = getRequest();
		var pathVariables = getPathVariables(request);
		return Long.valueOf(pathVariables.get("id"));
	}

	private HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		return request;
	}

	private Map<String, String> getPathVariables(HttpServletRequest request) {
		return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
	}

	private boolean hasOnlyRoleUser(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.allMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
	}
}
