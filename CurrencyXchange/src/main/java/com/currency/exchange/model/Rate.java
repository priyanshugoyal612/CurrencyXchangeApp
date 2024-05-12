package com.currency.exchange.model;

import java.math.BigDecimal;

import com.currency.exchange.enums.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Rate {

	BigDecimal amount;
	Currency fromCurrency;
	Currency toCurrency;

}