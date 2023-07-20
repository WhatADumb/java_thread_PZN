package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    @Test
    void testCreate() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(5);
        ExecutorService service = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 5; i++){
            service.execute(() -> {
                try {
                    System.out.println("Task on " + Thread.currentThread().getName() + ", status (waiting)");
                    barrier.await();
                    System.out.println("Task on " + Thread.currentThread().getName() + ", status (finish)");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
