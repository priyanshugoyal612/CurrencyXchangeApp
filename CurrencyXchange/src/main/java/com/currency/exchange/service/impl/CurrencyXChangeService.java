package com.currency.exchange.service.impl;

import java.math.BigDecimal;
import java.util.Objects;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.exchange.enums.Currency;
import com.currency.exchange.model.Rate;
import com.currency.exchange.service.CurrencyXchangeService;
import com.currency.exchange.service.ExchangeRateService;

@Service
public class CurrencyXChangeService implements CurrencyXchangeService {

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

	@Override
	public Rate getRate(Currency from, Currency to) {
		String cacheKey = from + "_" + to;
		BigDecimal exchangeRateCached = exchangeRateCache.get(cacheKey);
		if (exchangeRateCached == null) {
			exchangeRateCached = exchangeRateService.getExchangeRate(from, to);
			System.out.println(exchangeRateCached + "rate amount from service");
			exchangeRateCache.put(cacheKey, exchangeRateCached);
		}
		Rate rate = new Rate(exchangeRateCached, from, to);
		return rate;
	}

	@Override
	public BigDecimal getConversion(Currency from, Currency to, BigDecimal amount) {

		if (from == null || to == null || amount == null) {
			throw new IllegalArgumentException("Please provide the currencies for conversion or amount");
		}
		if (from.equals(to)) {
			System.out.println("From and to are same");
			return BigDecimal.ONE;
		}

		String cacheKey = from + "_" + to + "_" + amount;
		BigDecimal conversionAmount = conversionResultCache.get(cacheKey);
		if (conversionAmount == null) {
			Rate currentRate = getRate(from, to);
			if (Objects.nonNull(currentRate) && Objects.nonNull(currentRate.getAmount())) {
				conversionAmount = amount.multiply(currentRate.getAmount());
				conversionResultCache.put(cacheKey, conversionAmount);
				System.out.println("Not from cacche");
			}
		}

		return conversionAmount;
	}

}
