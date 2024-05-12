package com.currency.exchange.service.rest;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.currency.exchange.enums.Currency;
import com.currency.exchange.model.CurrencyResponse;
import com.currency.exchange.service.CurrencyXchangeService;

@RestController
@RequestMapping("/v1/api")
public class CurrencyXChangeRestService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CurrencyXchangeService currencyXChangeService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/exchange")
	public ResponseEntity<CurrencyResponse> exchangeService(@RequestParam Currency fromCurrency,
			@RequestParam Currency toCurrency, @RequestParam BigDecimal amount) {
		logger.info("Getting the amount using exchange service for {} to {} for amount {}", fromCurrency, toCurrency,
				amount);

		BigDecimal convertedAmount = currencyXChangeService.getConversion(fromCurrency, toCurrency, amount);
		
		CurrencyResponse.builder().amount(amount).fromCurrency(fromCurrency).toCurrency(toCurrency).convertedAmount(convertedAmount).build();
		return ResponseEntity.ok(CurrencyResponse.builder().amount(amount).fromCurrency(fromCurrency).toCurrency(toCurrency).convertedAmount(convertedAmount).build());
	}

}
