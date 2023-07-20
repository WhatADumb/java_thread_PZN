package study.java.thread;

import org.junit.jupiter.api.Test;
import study.java.thread.model.Counter;
import study.java.thread.model.LockCounter;
import study.java.thread.model.ReadWriteLockCounter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    @Test
    void testLockRaceCondition() throws InterruptedException {
        LockCounter counter = new LockCounter();

        Runnable run = () -> {
            for (int i = 1; i <= 1_000_000; i++){
                counter.increment();
            }
        };

        Thread thread1 = new Thread(run);
        Thread thread2 = new Thread(run);
        Thread thread3 = new Thread(run);

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Value: " + counter.getValue());
    }

    @Test
    void testReadWriteLockRaceCondition() throws InterruptedException {
        ReadWriteLockCounter counter = new ReadWriteLockCounter();

        Runnable run = () -> {
            for (int i = 1; i <= 1_000_000; i++){
                counter.increment();
            }
        };

        Thread thread1 = new Thread(run);
        Thread thread2 = new Thread(run);
        Thread thread3 = new Thread(run);

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Value: " + counter.getValue());
    }

    String message;

    @Test
    void testMonitorConditionRace() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(() -> {
            try {
                lock.lock();
                condition.await();
                System.out.println(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                lock.lock();
                condition.await();
                System.out.println(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                lock.lock();
                message = "Hello World";
                Thread.sleep(2000);
                condition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        });

        thread1.start();
        thread3.start();
        thread2.start();
        thread1.join();
        thread3.join();
        thread2.join();
    }
}
