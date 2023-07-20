package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {
    @Test
    void testCreate() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService service = Executors.newFixedThreadPool(10);

        service.execute(() -> {
            try {
                System.out.println("Thread-1 Push data: FIRST");
                var result = exchanger.exchange("FIRST");
                System.out.println("Thread-1 response: " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.execute(() -> {
            try {
                System.out.println("Thread-2 Push data: SECOND");
                var result = exchanger.exchange("SECOND");
                System.out.println("Thread-2 response: " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
