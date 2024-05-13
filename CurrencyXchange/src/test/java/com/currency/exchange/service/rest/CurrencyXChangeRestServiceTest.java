package com.currency.exchange.service.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.currency.exchange.helper.CurrencyValidator;
import com.currency.exchange.model.BaseCurrency;
import com.currency.exchange.model.CurrencyResponse;
import com.currency.exchange.service.CurrencyXchangeService;

@ExtendWith(MockitoExtension.class)
public class CurrencyXChangeRestServiceTest {

	@Mock
	private CurrencyXchangeService currencyXChangeService;

	@InjectMocks
	private CurrencyXChangeRestService currencyXChangeRestService;

	@Mock
	private CurrencyValidator currencyValidator;

	@Test
	public void testExchangeServiceBadInput() {
		// Arrange
		String fromCurrency = "EUR";
		String toCurrency = null;
		BigDecimal amount = BigDecimal.valueOf(100);

		ResponseEntity<CurrencyResponse> response = currencyXChangeRestService.exchangeService(fromCurrency, toCurrency,
				amount);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


	}

	@Test
	public void testExchangeService() {
		// Arrange
		String fromCurrency = null;
		String toCurrency = null;
		BigDecimal amount = BigDecimal.valueOf(100);

		ResponseEntity<CurrencyResponse> response = currencyXChangeRestService.exchangeService(fromCurrency, toCurrency,
				amount);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


	}

	@Test
	public void testGetAllCurrencies() {
		// Arrange
		List<BaseCurrency> currencies = new ArrayList<>();
		currencies.add(new BaseCurrency("USD", "United States Dollar"));
		currencies.add(new BaseCurrency("EUR", "Euro"));

		when(currencyXChangeService.getAllCurrencies()).thenReturn(currencies);

		// Act
		ResponseEntity<List<BaseCurrency>> response = currencyXChangeRestService.getAllCurrencies();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(currencies, response.getBody());
	}

}
