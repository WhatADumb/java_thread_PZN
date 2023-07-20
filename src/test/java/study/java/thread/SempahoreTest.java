package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SempahoreTest {
    @Test
    void testCreateAndRun() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(20);
        final ExecutorService service = Executors.newFixedThreadPool(100);

        for(int i = 0; i < 100; i++){
            final var task = i;
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println("Program: " + task);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    semaphore.release();
                }
            });
        }

        service.awaitTermination(1, TimeUnit.DAYS);
    }
}
