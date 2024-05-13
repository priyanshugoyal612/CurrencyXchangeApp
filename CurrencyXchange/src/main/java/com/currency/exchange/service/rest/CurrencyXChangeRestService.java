package com.currency.exchange.service.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.currency.exchange.helper.CurrencyValidator;
import com.currency.exchange.model.BaseCurrency;
import com.currency.exchange.model.CurrencyResponse;
import com.currency.exchange.service.CurrencyXchangeService;

@RestController
@RequestMapping("/v1/api")
public class CurrencyXChangeRestService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CurrencyXchangeService currencyXChangeService;

	/**
	 * Retrieves the converted amount using exchange service.
	 *
	 * @param fromCurrency The currency to convert from.
	 * @param toCurrency   The currency to convert to.
	 * @param amount       The amount to convert.
	 * @return ResponseEntity containing the conversion result.
	 */
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/exchange")
	public ResponseEntity<CurrencyResponse> exchangeService(@RequestParam String fromCurrency,
			@RequestParam String toCurrency, @RequestParam BigDecimal amount) {
		logger.info("Getting the amount using exchange service for {} to {} for amount {}", fromCurrency, toCurrency,
				amount);

		ResponseEntity<CurrencyResponse> validationErrorResponse = CurrencyValidator.validation(fromCurrency,
				toCurrency, amount);
		if (Objects.nonNull(validationErrorResponse)) {
			logger.warn("Currency exchange validation failed: {}", validationErrorResponse.getBody().getErrorMessage());
			return validationErrorResponse;
		}
		
		 fromCurrency = fromCurrency.toUpperCase(Locale.ENGLISH);
		 toCurrency = toCurrency.toUpperCase(Locale.ENGLISH);

		BigDecimal convertedAmount = currencyXChangeService.getConversion(fromCurrency, toCurrency, amount);

		CurrencyResponse.builder().amount(amount).fromCurrency(fromCurrency).toCurrency(toCurrency)
				.convertedAmount(convertedAmount).build();
		logger.info("Currency exchange successful. Converted amount: {}", convertedAmount);
		return ResponseEntity.ok(CurrencyResponse.builder().amount(amount).fromCurrency(fromCurrency)
				.toCurrency(toCurrency).convertedAmount(convertedAmount).build());
	}

	/**
	 * Retrieves all available currencies.
	 *
	 * @return ResponseEntity containing the list of currencies.
	 */
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/currencies")
	public ResponseEntity<List<BaseCurrency>> getAllCurrencies() {
		logger.info("Request received to retrieve all currencies.");

		List<BaseCurrency> currencies = currencyXChangeService.getAllCurrencies();

		if (currencies != null) {
			logger.info("Retrieved {} currencies.", currencies.size());
			return ResponseEntity.ok(currencies);
		} else {
			logger.warn("No currencies found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
