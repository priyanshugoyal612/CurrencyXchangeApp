package com.currency.exchange.config;

import java.util.HashSet;

import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currency.exchange.service.ExchangeRateService;

import jakarta.annotation.PostConstruct;

/**
 * Component responsible for initializing the 'currencies' cache with available
 * currency codes. This class retrieves currency codes from the
 * ExchangeRateService and populates the cache with them.
 */
@Component
public class InitalizedCurrencieCache {

	private static final Logger logger = LoggerFactory.getLogger(InitalizedCurrencieCache.class);

	@Autowired
	private ExchangeRateService exchangeRateService;
	@Autowired
	private CacheManager cacheManager;

	@PostConstruct
	public void initCurrenciesCache() {
		HashSet<String> currenices = exchangeRateService.getAllCurrenciesCode();
		if (currenices != null) {

			cacheManager.getCache("currencies", String.class, HashSet.class).put("availableCurrencies", currenices);
			logger.info("Initialized 'currencies' cache with {} available currencies " + currenices.size());
		} else {
			logger.warn("Failed to initialize 'currencies' cache: currency data is null");
		}

	}
}
