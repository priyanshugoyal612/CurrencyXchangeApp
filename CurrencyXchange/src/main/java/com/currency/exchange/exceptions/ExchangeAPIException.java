package com.currency.exchange.exceptions;

import org.springframework.web.client.HttpClientErrorException.Forbidden;

public class ExchangeAPIException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ExchangeAPIException(String message) {
		super(message);
	}

	public ExchangeAPIException(String message, Forbidden e) {
		super(message, e);
	}

}
