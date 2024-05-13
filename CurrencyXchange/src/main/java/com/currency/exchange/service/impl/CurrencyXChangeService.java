package com.currency.exchange.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.exchange.model.BaseCurrency;
import com.currency.exchange.model.Rate;
import com.currency.exchange.service.CurrencyXchangeService;
import com.currency.exchange.service.ExchangeRateService;

@Service
public class CurrencyXChangeService implements CurrencyXchangeService {
	
	private final Logger logger = LoggerFactory.getLogger(CurrencyXChangeService.class);

	@Autowired
	private ExchangeRateService exchangeRateService;
	
	private final Cache<String, BigDecimal> exchangeRateCache;
	private final Cache<String, BigDecimal> conversionResultCache;

	@Autowired
	public CurrencyXChangeService(CacheManager cacheManager) {
		// Retrieve caches from the CacheManager
		exchangeRateCache = cacheManager.getCache("exchangeRates", String.class, BigDecimal.class);
		conversionResultCache = cacheManager.getCache("conversionResults", String.class, BigDecimal.class);
	}

	/**
     * Retrieves the exchange rate between two currencies.
     *
     * @param from The currency code to convert from.
     * @param to   The currency code to convert to.
     * @return Rate object containing the exchange rate.
     */
	@Override
	public Rate getRate(String from, String to) {
		String cacheKey = from + "_" + to;
		BigDecimal exchangeRateCached = exchangeRateCache.get(cacheKey);
		if (exchangeRateCached == null) {
			exchangeRateCached = exchangeRateService.getExchangeRate(from, to);
		    logger.debug("Retrieved exchange rate from service: {}", exchangeRateCached);
			exchangeRateCache.put(cacheKey, exchangeRateCached);
		}
		Rate rate = new Rate(exchangeRateCached, from, to);
		return rate;
	}

	  /**
     * Converts an amount from one currency to another.
     *
     * @param from   The currency code to convert from.
     * @param to     The currency code to convert to.
     * @param amount The amount to convert.
     * @return The converted amount.
     */
	@Override
	public BigDecimal getConversion(String from, String to, BigDecimal amount) {

		if (from == null || to == null || amount == null) {
			throw new IllegalArgumentException("Please provide the currencies for conversion or amount");
		}
		if (from.equals(to)) {
			logger.debug("Source and target currencies are the same: {}", from);
			return BigDecimal.ONE;
		}

		String cacheKey = from + "_" + to + "_" + amount;
		BigDecimal conversionAmount = conversionResultCache.get(cacheKey);
		if (conversionAmount == null) {
			Rate currentRate = getRate(from, to);
			if (Objects.nonNull(currentRate) && Objects.nonNull(currentRate.getAmount())) {
				conversionAmount = amount.multiply(currentRate.getAmount());
				conversionResultCache.put(cacheKey, conversionAmount);
				  logger.debug("Converted amount: {}", conversionAmount);
			}
		}

		return conversionAmount;
	}

	 /**
     * Retrieves all available currencies.
     *
     * @return List of BaseCurrency objects representing the available currencies.
     */
	@Override
	public List<BaseCurrency> getAllCurrencies() {
		return exchangeRateService.getAllCurrencies();
	}

}
