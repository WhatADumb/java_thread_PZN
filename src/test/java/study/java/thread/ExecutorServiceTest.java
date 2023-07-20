package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    @Test
    void testCreateSingle() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 100; i++){
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Thread Name: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testCreateFixed() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for(int i = 0; i < 100; i++){
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Thread Name: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
