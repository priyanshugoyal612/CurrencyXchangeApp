package com.currency.exchange.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.currency.exchange.exceptions.CurrencyFetchException;
import com.currency.exchange.exceptions.ExchangeAPIException;
import com.currency.exchange.model.BaseCurrency;
import com.currency.exchange.service.ExchangeRateService;

/**
 * Implementation of ExchangeRateService to fetch exchange rates and currency
 * information from an external API.
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

	@Value("${exchange.rate.api.url}")
	private String exchangeRateApiUrl;

	@Value("${exchange.rate.api.key}")
	private String exchangeRateApiKey;

	@Value("${exchange.rest.currencies}")
	private String currencieApiUrl;

	@Override
	public BigDecimal getExchangeRate(String from, String to) {
		LOGGER.info("Fetching exchange rate for {} to {}...", from, to);
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = exchangeRateApiUrl + from + "/" + to + "?api-key=" + exchangeRateApiKey;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-Key", exchangeRateApiKey);

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity,
					Map.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				Map<String, Object> response = responseEntity.getBody();
				if (response != null && response.containsKey("quote")) {
					Object rateValue = response.get("quote");
					System.out.println(rateValue);
					if (rateValue instanceof Number) {
						BigDecimal exchangeRate = BigDecimal.valueOf(((Number) rateValue).doubleValue());
						LOGGER.info("Exchange rate fetched successfully: {} -> {} : {}", from, to, exchangeRate);
						return exchangeRate;
					}
				}
			}

		} catch (HttpClientErrorException.Forbidden e) {
			LOGGER.error("Access to the exchange rate API is forbidden.");
			throw new CurrencyFetchException("Some Base currenices are forbidden for developer plan ");
		} catch (Exception e) {
			LOGGER.error("Error fetching exchange rate: {}", e.getMessage());
			throw new ExchangeAPIException("Error fetching exchange rate: " + e.getMessage());
		}

		LOGGER.warn("Failed to fetch exchange rate for {} to {}", from, to);
		return null;

	}

	@Override
	public List<BaseCurrency> getAllCurrencies() {
		LOGGER.info("Fetching all currencies...");
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = currencieApiUrl + "?api-key=" + exchangeRateApiKey;
		System.out.println(apiUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-Key", exchangeRateApiKey);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Map<String, String>>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET,
				requestEntity, ParameterizedTypeReference.forType(List.class));

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			List<BaseCurrency> currencies = new ArrayList<>();
			List<Map<String, String>> currenciesData = responseEntity.getBody();
			if (currenciesData != null) {
				for (Map<String, String> currencyData : currenciesData) {
					String code = currencyData.get("code");
					String name = currencyData.get("name");
					currencies.add(new BaseCurrency(code, name));
				}
			}
			LOGGER.info("All currency codes fetched successfully.");
			return currencies;
		} else {
			LOGGER.warn("Failed to fetch currency codes: {}", responseEntity.getStatusCode());
			return null;
		}

	}

	@Override
	public HashSet<String> getAllCurrenciesCode() {
		LOGGER.info("Fetching all currencies...");
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = currencieApiUrl + "?api-key=" + exchangeRateApiKey;

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-Key", exchangeRateApiKey);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Map<String, String>>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET,
				requestEntity, ParameterizedTypeReference.forType(List.class));

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			HashSet<String> currencies = new HashSet<String>();
			List<Map<String, String>> currenciesData = responseEntity.getBody();
			if (currenciesData != null) {
				for (Map<String, String> currencyData : currenciesData) {
					String code = currencyData.get("code");
					currencies.add(code);
				}
			}
			LOGGER.info("All currencies codes fetched successfully.");
			return currencies;
		} else {
			LOGGER.warn("Failed to fetch currencies codes: {}", responseEntity.getStatusCode());
			return null;
		}

	}

}
