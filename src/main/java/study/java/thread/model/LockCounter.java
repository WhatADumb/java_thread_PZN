package study.java.thread.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter {
    private Long value = 0L;

    private final Lock lock = new ReentrantLock();

    public void increment(){
        try{
            lock.lock();
            value++;
        }finally {
            lock.unlock();
        }
    }

    public Long getValue(){
        return value;
    }
}
