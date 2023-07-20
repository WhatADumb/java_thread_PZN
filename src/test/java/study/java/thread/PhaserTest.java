package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest {
    @Test
    void testAsCountDownLatch() throws InterruptedException {
        Phaser phaser = new Phaser();
        ExecutorService service = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);
        for(int i = 0; i < 5; i++){
            service.execute(() -> {
                try {
                    System.out.println("Task is waiting");
                    Thread.sleep(2000);
                    System.out.println("Task is done");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    phaser.arrive();
                }
            });
        }

        service.execute(() -> {
            phaser.awaitAdvance(0);
            System.out.println("All Task are clear");
        });

        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testAsCyclicBarrier() throws InterruptedException {
        Phaser phaser = new Phaser();
        ExecutorService service = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);
        for(int i = 0; i < 5; i++){
            service.execute(() -> {
                System.out.println("Waiting program queued");
                phaser.arriveAndAwaitAdvance();
                System.out.println("Finished program queued");
            });
        }

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
