package com.webflux.springreactive.utils;

import org.springframework.stereotype.Component;

@Component
public class SleepUtils {
    public void sleepSeconds(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
