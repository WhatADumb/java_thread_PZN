package study.java.thread;

import org.junit.jupiter.api.Test;
import study.java.thread.model.AtomicCounter;
import study.java.thread.model.Counter;

public class AtomicTest {
    @Test
    void testRace() throws InterruptedException {
        var counter = new AtomicCounter();

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
}
