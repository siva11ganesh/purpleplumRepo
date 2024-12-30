package com.products.exception;

import lombok.Getter;

@Getter
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException(String errorMsg) {
		super(errorMsg);
	}
}
