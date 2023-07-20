package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExcutorServiceTest {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

    @Test
    void testDelayed() throws InterruptedException {
        var data = service.schedule(() -> System.out.println("Hello Data"), 2, TimeUnit.SECONDS);
        System.out.println(data.getDelay(TimeUnit.SECONDS));

        service.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    void testScheduled() throws InterruptedException {
        var data = service.scheduleAtFixedRate(() -> System.out.println("Hello Data"), 2, 1, TimeUnit.SECONDS);
        System.out.println(data.getDelay(TimeUnit.SECONDS));

        service.awaitTermination(5, TimeUnit.SECONDS);
    }
}
