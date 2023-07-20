package study.java.thread.model;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter {
    private AtomicLong value = new AtomicLong(0L);

    public AtomicLong getValue() {
        return value;
    }

    public void increment(){
        value.incrementAndGet();
    }
}
