package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ThreadLocalRandomTest {
    @Test
    void testThreadLocalRandom() throws InterruptedException {
        var thread = Executors.newFixedThreadPool(10);


        for(int i = 0; i < 10; i++){
            thread.execute(() -> {
                try {
                    int numbers = ThreadLocalRandom.current().nextInt();
                    Thread.sleep(1000);
                    System.out.println(numbers);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void threadRandomStream() throws InterruptedException {
        var thread = Executors.newFixedThreadPool(10);

        thread.execute(() -> {
            ThreadLocalRandom.current().ints(100, 1, 100)
                    .forEach(System.out::println);
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }
}
