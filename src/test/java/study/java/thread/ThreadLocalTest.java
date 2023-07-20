package study.java.thread;

import org.junit.jupiter.api.Test;
import study.java.thread.model.UserService;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    @Test
    void testThreadLocal() throws InterruptedException {
        var random = new Random();
        var user = new UserService();
        var thread = Executors.newFixedThreadPool(100);

        for(int i = 0; i < 100; i++){
            final var data = i;
            thread.execute(() -> {
                try {
                    user.setUser("user-"+data);
                    Thread.sleep(random.nextInt(1000, 3000));
                    user.doAction();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }
}
