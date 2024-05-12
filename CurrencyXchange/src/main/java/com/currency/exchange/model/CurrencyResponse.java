package com.currency.exchange.model;

import java.math.BigDecimal;

import com.currency.exchange.enums.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyResponse {

	Currency fromCurrency;
	Currency toCurrency;
	BigDecimal amount;
	BigDecimal convertedAmount;

}
