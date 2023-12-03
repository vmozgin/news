package com.example.news.exception;

public class AccessDeniedException extends RuntimeException{

	public AccessDeniedException(String message) {
		super(message);
	}
}
