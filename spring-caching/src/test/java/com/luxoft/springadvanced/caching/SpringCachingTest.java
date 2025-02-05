package com.luxoft.springadvanced.caching;

import com.luxoft.springadvanced.caching.config.CachingConfig;
import com.luxoft.springadvanced.caching.model.Client;
import com.luxoft.springadvanced.caching.model.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CachingConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringCachingTest {

    @Autowired
    private ClientService service;

    @Test
    public void testCacheableAddress() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        long time1 = System.nanoTime();
        service.getCacheableAddress(john);
        john.setAddress("New address");
        long time2 = System.nanoTime();
        assertEquals("Bucharest, Calea Floreasca 167", service.getCacheableAddress(john),
                () -> "The address should be taken from the cache, so unchanged");
        long time3 = System.nanoTime();
        long timeDiff1 = (time2-time1)/1000_000;
        long timeDiff2 = (time3-time2)/1000_000;
        System.out.println("testCacheableAddress() ");
        System.out.println("Duration of the first execution  (address retrieved from client): "
                + timeDiff1);
        System.out.println("Duration of the second execution (address retrieved from cache): "
                + timeDiff2);
    }

    @Test
    public void testCacheableAddressMultipleCaches() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        long time1 = System.nanoTime();
        service.getCacheableAddressMultipleCaches(john);
        john.setAddress("New address");
        long time2 = System.nanoTime();
        assertEquals("Bucharest, Calea Floreasca 167",
                service.getCacheableAddressMultipleCaches(john),
                () -> "The address should be taken from the cache, so unchanged");
        long time3 = System.nanoTime();
        long timeDiff1 = (time2-time1)/1000_000;
        long timeDiff2 = (time3-time2)/1000_000;
        System.out.println("Duration of the first execution  (address retrieved from client): " + timeDiff1);
        System.out.println("Duration of the second execution (address retrieved from cache): " + timeDiff2);
    }

    @Test
    public void testAddressCacheEvict() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        long time1 = System.nanoTime();
        service.getAddressCacheEvict(john);
        john.setAddress("New address");
        long time2 = System.nanoTime();
        assertEquals("New address", service.getAddressCacheEvict(john),
                () -> "The address should be updated");
        long time3 = System.nanoTime();
        long timeDiff1 = (time2-time1)/1000_000;
        long timeDiff2 = (time3-time2)/1000_000;
        System.out.println("Duration of the first execution  (address retrieved from client): " + timeDiff1);
        System.out.println("Duration of the second execution (address retrieved from cache): " + timeDiff2);
    }

    @Test
    public void testAddressCacheEvictSelectively() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        long time1 = System.nanoTime();
        service.getCacheableAddress(john);
        john.setAddress("New address");
        service.getAddressCacheEvictSelectively(john);
        long time2 = System.nanoTime();
        assertEquals("New address", service.getCacheableAddress(john),
                () -> "The address should be updated");
        long time3 = System.nanoTime();
        long timeDiff1 = (time2-time1)/1000_000;
        long timeDiff2 = (time3-time2)/1000_000;
        System.out.println("Duration of the first execution  (address retrieved from client): " + timeDiff1);
        System.out.println("Duration of the second execution (address retrieved from cache): " + timeDiff2);
    }

    @Test
    public void testAddressCacheableNoParameters() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");
        long time1 = System.nanoTime();
        service.getAddressCacheableNoParameters(john);
        john.setAddress("New address");
        long time2 = System.nanoTime();

        // cache is not used here because client.hashCode() is based on name and has not changed
        // See details of the default key generation at SimpleKeyGenerator.generateKey():
        // if there's 1 parameter, use it's hashCode as a key,
        // if more - create a hash code from all params
        // also it's possible to implement interface KeyGenerator by overriding
        // Object KeyGenerator.generate(Object target, Method method, Object... params)

        assertEquals("Bucharest, Calea Floreasca 167",
                service.getAddressCacheableNoParameters(john),
                () -> "The address should be taken from the cache, so unchanged");
        long time3 = System.nanoTime();
        long timeDiff1 = (time2-time1)/1000_000;
        long timeDiff2 = (time3-time2)/1000_000;
        System.out.println("Duration of the first execution  (address retrieved from client): " + timeDiff1);
        System.out.println("Duration of the second execution (address retrieved from cache): " + timeDiff2);
    }

    @Test
    public void testAddressCachePutCondition() {
        Client john = new Client("John", "Bucharest, Calea Floreasca 167");

        long time0 = System.nanoTime();
        service.getCacheableAddress(john);

        long time1 = System.nanoTime();
        service.getAddressCachePutCondition(john);
        john.setAddress("New address");
        long time2 = System.nanoTime();
        assertEquals("Bucharest, Calea Floreasca 167", service.getCacheableAddress(john),
                () -> "The address should be updated");
        long time3 = System.nanoTime();
        long timeDiff0 = (time1-time0)/1000_000;
        long timeDiff1 = (time2-time1)/1000_000;
        long timeDiff2 = (time3-time2)/1000_000;
        System.out.println("Duration of the first execution  (address retrieved from client): " + timeDiff0);
        System.out.println("Duration of the second execution  (address retrieved from client): " + timeDiff1);
        System.out.println("Duration of the third execution (address retrieved from cache): " + timeDiff2);

        Client mike = new Client("Mike", "Bucharest, Calea Floreasca 167");
        time1 = System.nanoTime();
        service.getAddressCachePutCondition(mike);
        mike.setAddress("New address");
        time2 = System.nanoTime();
        assertEquals("New address", service.getCacheableAddress(mike),
                () -> "The address should be updated");
        time3 = System.nanoTime();

        timeDiff1 = (time2-time1)/1000_000;
        timeDiff2 = (time3-time2)/1000_000;
        System.out.println("Duration of the first execution  (address retrieved from client): " + timeDiff1);
        System.out.println("Duration of the second execution (address retrieved from cache): " + timeDiff2);
    }

}
