package com.airhacks.stress;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class StressManager {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @PostConstruct
    public void construction() {
        int count = COUNTER.incrementAndGet();
        System.out.println("Instances: " + count);
    }

    public String stress() {
        return "heavy " + System.currentTimeMillis();
    }

}
