package com.currency.exchange.exceptions;

import org.springframework.web.client.HttpClientErrorException.Forbidden;

public class CurrencyFetchException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrencyFetchException(String message) {
		super(message);
	}

	public CurrencyFetchException(String message, Forbidden e) {
		super(message, e);
	}
} 
