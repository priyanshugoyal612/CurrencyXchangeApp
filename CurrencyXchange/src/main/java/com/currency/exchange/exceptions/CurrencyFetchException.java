package com.currency.exchange.exceptions;

import org.springframework.web.client.HttpClientErrorException.Forbidden;

public class CurrencyFetchException extends RuntimeException {
	public CurrencyFetchException(String message) {
		super(message);
	}

	public CurrencyFetchException(String message, Forbidden e) {
		super(message, e);
	}
}
