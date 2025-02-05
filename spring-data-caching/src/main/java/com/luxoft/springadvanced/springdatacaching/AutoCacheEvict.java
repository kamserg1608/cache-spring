package com.luxoft.springadvanced.springdatacaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Configuration
public class AutoCacheEvict {
    @Autowired
    CacheManager cacheManager;

    public void evictAllCaches() {
        cacheManager.getCacheNames().forEach(
                cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Scheduled(fixedRate = 3000)
    public void evictAllcachesAtIntervals() {
        System.out.println("evicting cache...");
        evictAllCaches();
    }

}
