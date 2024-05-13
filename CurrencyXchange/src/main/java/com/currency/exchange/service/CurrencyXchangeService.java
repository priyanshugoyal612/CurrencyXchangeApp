package com.currency.exchange.service;

import java.math.BigDecimal;
import java.util.List;

import com.currency.exchange.enums.Currency;
import com.currency.exchange.model.BaseCurrency;
import com.currency.exchange.model.Rate;

/**
 * Service interface for handling currency exchange operations.
 */
public interface CurrencyXchangeService {

	/**
	 * Retrieves a list of all available currencies.
	 *
	 * @return List of BaseCurrency objects representing the available currencies.
	 */
	public List<BaseCurrency> getAllCurrencies();

	/**
	 * Retrieves the exchange rate between two currencies.
	 *
	 * @param from The currency code to convert from.
	 * @param to   The currency code to convert to.
	 * @return Rate object containing the exchange rate.
	 */
	public Rate getRate(String from, String to);

	/**
	 * Converts an amount from one currency to another.
	 *
	 * @param from   The currency code to convert from.
	 * @param to     The currency code to convert to.
	 * @param amount The amount to convert.
	 * @return The converted amount.
	 */
	public BigDecimal getConversion(String from, String to, BigDecimal amount);

}
