package com.currency.exchange.helper;

import java.math.BigDecimal;
import java.util.HashSet;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.currency.exchange.model.CurrencyResponse;

/**
 * Helper class for validating currency-related operations. This class provides
 * methods to validate currency codes and perform currency exchange validations.
 */
@Component
public class CurrencyValidator {

	private static final Logger logger = LoggerFactory.getLogger(CurrencyValidator.class);

	/**
	 * Setter method for injecting the CacheManager instance.
	 *
	 * @param cacheManager The CacheManager instance to be injected.
	 */
	private static CacheManager cacheManager;

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		CurrencyValidator.cacheManager = cacheManager;
	}

	/**
	 * Validates currency exchange parameters.
	 *
	 * @param fromCurrency The currency code to convert from.
	 * @param toCurrency   The currency code to convert to.
	 * @param amount       The amount of currency to exchange.
	 * @return A ResponseEntity containing the validation result.
	 */
	public static ResponseEntity<CurrencyResponse> validation(String fromCurrency, String toCurrency,
			BigDecimal amount) {

		logger.debug("Performing currency validation...");
		if (!CurrencyValidator.isValidCurrency(fromCurrency)) {
			logger.warn("Invalid from currency: {}", fromCurrency);
			return ResponseEntity.badRequest().body(CurrencyResponse.builder().errorMessage("Invalid from Currency")
					.fromCurrency(fromCurrency).build());
		}
		if (!CurrencyValidator.isValidCurrency(toCurrency)) {
			logger.warn("Invalid to currency: {}", toCurrency);
			return ResponseEntity.badRequest().body(
					CurrencyResponse.builder().errorMessage("Invalid to Currency").toCurrency(toCurrency).build());
		}

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			logger.warn("Invalid amount: {}", amount);
			return ResponseEntity.badRequest()
					.body(CurrencyResponse.builder().errorMessage("Invalid amount ").amount(amount).build());
		}
		logger.debug("Currency validation successful.");
		return null;
	}

	/**
	 * Validates a currency code by checking if it exists in the available
	 * currencies cache.
	 *
	 * @param currency The currency code to validate.
	 * @return true if the currency code is valid, false otherwise.
	 */
	public static boolean isValidCurrency(String currency) {
		if (!StringUtils.hasText(currency) || currency == null || cacheManager == null) {
			return false;
		}
		Cache<String, HashSet> currenciesCache = cacheManager.getCache("currencies", String.class, HashSet.class);
		if (currenciesCache == null) {
			return false;
		}
		HashSet<String> availableCurrencies = currenciesCache.get("availableCurrencies");
		return availableCurrencies != null && availableCurrencies.contains(currency.toUpperCase());
	}

}
