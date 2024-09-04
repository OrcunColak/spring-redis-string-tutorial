package com.colak.springtutorial.stringvalue.opsforvalue;

import com.colak.springtutorial.stringvalue.OpsForValueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class OpsForValueServiceTest {

    @Autowired
    private OpsForValueService opsForValueService;

    @Test
    void testDelete() {
        String key = "key1";
        String value = "value";
        Boolean result = opsForValueService.setIfAbsent(key, value, 5L, TimeUnit.SECONDS);
        assertEquals(Boolean.TRUE, result);

        result = opsForValueService.delete(key);
        assertEquals(Boolean.TRUE, result);

        Object getValue = opsForValueService.getAndExpire(key, Duration.ofMinutes(1));
        assertNull(getValue);
    }

    @Test
    void testGenerateId() {
        String key = "mycounter";
        opsForValueService.delete(key);

        Long result = opsForValueService.increment(key);
        assertEquals(Long.valueOf(1), result);

        opsForValueService.set(key,String.valueOf(2L));
        Object object = opsForValueService.get(key);
        assertEquals("2", object);
    }
}
