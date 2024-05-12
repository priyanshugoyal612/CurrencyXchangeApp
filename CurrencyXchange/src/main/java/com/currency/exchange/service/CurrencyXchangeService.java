package com.currency.exchange.service;

import java.math.BigDecimal;

import com.currency.exchange.enums.Currency;
import com.currency.exchange.model.Rate;

/**
 * 
 */
public interface CurrencyXchangeService {

	public Rate getRate(Currency from, Currency to);

	public BigDecimal getConversion(Currency from, Currency to, BigDecimal amount);

}
