package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    @Test
    void testCreate() {
        int minThread = 5;
        int maxThread = 75;
        int live = 1;
        var liveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(10);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(minThread, maxThread, live, liveTime, queue);
    }

    @Test
    void testRunnable() throws InterruptedException {
        int minThread = 5;
        int maxThread = 75;
        int live = 1;
        var liveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(10);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(minThread, maxThread, live, liveTime, queue);

        Runnable run = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task Thread Name: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        poolExecutor.execute(run);
        Thread.sleep(2500);
    }

    @Test
    void testShutdown() throws InterruptedException {
        int minThread = 5;
        int maxThread = 75;
        int live = 1;
        var liveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(10);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(minThread, maxThread, live, liveTime, queue);

        for (int i = 0; i < 50; i++){
            final int task = i;
            Runnable run = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task: " + task + " Thread Name:  " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            poolExecutor.execute(run);
        }


        poolExecutor.shutdown();
        poolExecutor.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class LogRejectedHandler implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task " + r.toString() + " is failed cause of Over Memory Thread");
        }
    }

    @Test
    void testHandler() throws InterruptedException {
        int minThread = 5;
        int maxThread = 75;
        int live = 1;
        var liveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(10);

        var log = new LogRejectedHandler();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(minThread, maxThread, live, liveTime, queue, log);

        for (int i = 0; i < 150; i++){
            final int task = i;
            Runnable run = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task: " + task + " Thread Name:  " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            poolExecutor.execute(run);
        }


        poolExecutor.shutdown();
        poolExecutor.awaitTermination(1, TimeUnit.DAYS);
    }
}
