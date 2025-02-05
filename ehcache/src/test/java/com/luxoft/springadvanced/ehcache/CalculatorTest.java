package com.luxoft.springadvanced.ehcache;

import com.luxoft.springadvanced.ehcache.calculator.Calculator;
import com.luxoft.springadvanced.ehcache.config.CacheHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private Calculator calculator = new Calculator();
    private CacheHelper cacheHelper = new CacheHelper();

    @BeforeEach
    void setup() {
        calculator.setCache(cacheHelper);
    }

    @Test
    void calculateOnceTest() {
        for (int i = 1; i <= 10; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
            System.out.println("Square value of " + i + ": " + calculator.getSquareValueOfNumber(i) + "\n");
        }
    }

    @Test
    void calculateTwiceTest() {
        int cached = 0;
        int uncached = 0;
        for (int i = 1; i <= 20; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
            System.out.println("Square value of " + i + ": " + calculator.getSquareValueOfNumber(i) + "\n");
        }

        List<Integer> c = new ArrayList<>();
        for (int i=1; i <= 20; i++) {
            if (cacheHelper.getSquareNumberCache().containsKey(i)) {
                cached++;
                c.add(i);
            } else {
                uncached++;
            }
        }

        assertEquals(10, cached);
        assertEquals(10, uncached);

    }

    @Test
    void dataExpiryTest() throws InterruptedException {
        cacheHelper.configureSquareNumberCache(10, 5);

        for (int i = 1; i <= 10; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
            System.out.println("Square value of " + i + ": " + calculator.getSquareValueOfNumber(i) + "\n");
        }

        for (int i = 1; i <= 10; i++) {
            assertTrue(cacheHelper.getSquareNumberCache().containsKey(i));
        }

        Thread.sleep(5000);

        for (int i = 1; i <= 10; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
        }
    }

}
