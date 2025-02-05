package com.luxoft.springadvanced.caching.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = { "addresses" })
public class ClientService {

    @Cacheable(value = "addresses", key = "#client.name")
    public String getCacheableAddress(Client client) {
        return client.getAddress();
    }

    @Cacheable({ "addresses", "directory" })
    public String getCacheableAddressMultipleCaches(Client client) {
        return client.getAddress();
    }

    @CacheEvict(value = "addresses", allEntries = true)
    public String getAddressCacheEvict(Client client) {
        return client.getAddress();
    }

    @Caching(evict = {
            @CacheEvict(value="addresses", key = "#client.name"),
            @CacheEvict("directory")
    })
    public void getAddressCacheEvictSelectively(Client client) {
    }

    @Cacheable
    public String getAddressCacheableNoParameters(Client client1) {
        return client1.getAddress();
    }

    @CachePut(value = "addresses", key="#client.name", condition = "#client.name=='John'")
    public String getAddressCachePutCondition(Client client) {
        return client.getAddress();
    }

}
