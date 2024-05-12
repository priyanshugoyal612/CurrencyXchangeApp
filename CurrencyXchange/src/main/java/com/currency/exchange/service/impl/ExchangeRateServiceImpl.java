package com.currency.exchange.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.currency.exchange.enums.Currency;
import com.currency.exchange.service.ExchangeRateService;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	
	@Value("${exchange.rate.api.url}")
	private String exchangeRateApiUrl;
	
	 @Value("${exchange.rate.api.key}")
	    private String exchangeRateApiKey;

	@Override
	public BigDecimal getExchangeRate(Currency from, Currency to) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = exchangeRateApiUrl  + from + "/" + to +"?api-key=" + exchangeRateApiKey;
		System.out.println(apiUrl);
		
			HttpHeaders headers = new HttpHeaders();
	        headers.set("X-API-Key", exchangeRateApiKey);

	        // Create request entity with headers
	        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

	        // Make HTTP GET request to fetch exchange rate
	        ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, Map.class);
	        System.out.println(responseEntity.toString());

		
		
	        if (responseEntity.getStatusCode().is2xxSuccessful()) {
	            Map<String, Object> response = responseEntity.getBody();
	            if (response != null && response.containsKey("quote")) {
	                Object rateValue = response.get("quote");
	                System.out.println(rateValue);
	                if (rateValue instanceof Number) {
	                    return BigDecimal.valueOf(((Number) rateValue).doubleValue());
	                }
	            }
	        }

        // If exchange rate cannot be fetched or parsed, return null or handle error as needed
        return null;
	}

}
