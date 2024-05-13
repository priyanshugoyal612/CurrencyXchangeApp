package com.currency.exchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String errorMessage = "Invalid parameter value: " + ex.getName() + " - " + ex.getRequiredType().getSimpleName();
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
		String errorMessage = "Missing request parameter: " + ex.getParameterName();
		return ResponseEntity.badRequest().body(errorMessage);
	}

	@ExceptionHandler(CurrencyFetchException.class)
	public ResponseEntity<CurrencyFetchException> handleForbiddenException(CurrencyFetchException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex);
	}
	
	@ExceptionHandler(ExchangeAPIException.class)
	public ResponseEntity<ExchangeAPIException> handleExchangeAPIException(ExchangeAPIException ex) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex);
	}

}
