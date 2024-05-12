package com.currency.exchange.config;

import java.math.BigDecimal;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
public class EhCacheConfiguration {

	@Bean
	public CacheManager cacheManager() {

		return CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("exchangeRates",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, BigDecimal.class,
								ResourcePoolsBuilder.heap(1000)).build())
				.withCache("conversionResults", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(String.class, BigDecimal.class, ResourcePoolsBuilder.heap(1000))
						.build())
				.build(true);

	}
}
