package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    @Test
    void testCreate() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 5; i++){
            service.execute(() -> {
                try {
                    System.out.println("Starting Task");
                    Thread.sleep(2000);
                    System.out.println("Finishing Task");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        service.execute(() -> {
            try {
                countDownLatch.await();
                System.out.println("All Task Clear");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
