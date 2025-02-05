package com.luxoft.springadvanced.ehcache.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

public class CacheHelper {

    private CacheManager cacheManager;
    private Cache<Integer, Integer> squareNumberCache;

    public CacheHelper() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        squareNumberCache = cacheManager.createCache("calculator",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Integer.class, Integer.class, ResourcePoolsBuilder.heap(10)));
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public Cache<Integer, Integer> getSquareNumberCache() {
        return squareNumberCache;
    }

    public void configureSquareNumberCache(long entries, long seconds) {
        cacheManager.removeCache("calculator");
        squareNumberCache
                = cacheManager.createCache("calculator", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class, Integer.class,
                        ResourcePoolsBuilder.heap(entries))
                .withExpiry(ExpiryPolicyBuilder
                        .timeToLiveExpiration(Duration.ofSeconds(seconds)))
                .build());
    }

}
