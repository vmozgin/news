package com.example.news.exception;

public class EntityNotFoundException extends RuntimeException{

	public EntityNotFoundException(String message) {
		super(message);
	}
}
