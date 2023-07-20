package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {

    private ExecutorService service = Executors.newFixedThreadPool(10);
    private CompletionService<String> completionService = new ExecutorCompletionService<>(service);
    private Random random = new Random();

    @Test
    void testService() throws InterruptedException {
        Executors.newSingleThreadExecutor().execute(() -> {
            for (int i = 0; i < 100; i++){
                final var val = i;

                completionService.submit(() -> {
                    Thread.sleep(random.nextInt(1000, 3000));
                    return "Task: " + val;
                });
            }
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            while (true){
                try {
                    Future<String> future = completionService.poll(2, TimeUnit.SECONDS);
                    if (future == null) {
                        break;
                    }else {
                        System.out.println(future.get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        service.awaitTermination(5, TimeUnit.SECONDS);
    }
}
