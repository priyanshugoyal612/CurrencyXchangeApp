package com.currency.exchange.config;

import java.math.BigDecimal;
import java.util.HashSet;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Ehcache caches. This class defines cache
 * configurations for exchange rates, conversion results, and currencies.
 */
@Configuration
public class EhCacheConfiguration {

	/**
	 * Configures and initializes the Ehcache CacheManager with multiple caches.
	 *
	 * @return The initialized CacheManager instance.
	 */

	@Bean
	public CacheManager cacheManager() {

		return CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("exchangeRates",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, BigDecimal.class,
								ResourcePoolsBuilder.heap(1000)).build())
				.withCache("conversionResults",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, BigDecimal.class,
								ResourcePoolsBuilder.heap(1000)).build())
				.withCache("currencies",
						CacheConfigurationBuilder
								.newCacheConfigurationBuilder(String.class, HashSet.class, ResourcePoolsBuilder.heap(5))
								.build())
				.build(true);

	}
}
