package com.currency.exchange.service;

import java.math.BigDecimal;

import com.currency.exchange.enums.Currency;

public interface ExchangeRateService {

	public BigDecimal getExchangeRate(Currency from, Currency to);
}
