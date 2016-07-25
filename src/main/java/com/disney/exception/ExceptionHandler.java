package com.disney.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody Error handleNotFoundException(final Exception exception) {
		return new Error(exception.getMessage(), HttpStatus.NOT_FOUND.value());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public @ResponseBody Error handleGeneralException(final Exception exception) {
		return new Error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
}
