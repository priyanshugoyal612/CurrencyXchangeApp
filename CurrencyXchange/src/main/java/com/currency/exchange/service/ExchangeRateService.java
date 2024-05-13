package com.currency.exchange.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import com.currency.exchange.model.BaseCurrency;

/**
 * Service interface for handling exchange rate operations from external API.
 */
public interface ExchangeRateService {

	/**
	 * Retrieves the exchange rate between two currencies.
	 *
	 * @param from The currency code to convert from.
	 * @param to   The currency code to convert to.
	 * @return The exchange rate from currency 'from' to currency 'to'.
	 */
	public BigDecimal getExchangeRate(String from, String to);

	/**
	 * Retrieves a list of all available currencies.
	 *
	 * @return List of BaseCurrency objects representing the available currencies.
	 */
	public List<BaseCurrency> getAllCurrencies();

	/**
	 * Retrieves a set of all available currency codes.
	 *
	 * @return HashSet containing all currency codes.
	 */
	public HashSet<String> getAllCurrenciesCode();
}
