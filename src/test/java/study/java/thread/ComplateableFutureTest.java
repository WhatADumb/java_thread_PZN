package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class ComplateableFutureTest {
    private ExecutorService service = Executors.newFixedThreadPool(10);
    private Random random = new Random();

    public CompletableFuture<String> getValue(){
        CompletableFuture<String> value = new CompletableFuture<>();

        service.execute(() -> {
            try {
                Thread.sleep(3000);
                value.complete("Hello Everyone");
            } catch (InterruptedException e) {
                value.completeExceptionally(e);
            }
        });

        return value;
    }

    @Test
    void testCreate() throws ExecutionException, InterruptedException {
        Future<String> future = getValue();
        System.out.println(future.get());
    }

    public void execute(CompletableFuture<String> future, String name){
        service.execute(() -> {
            try {
                Thread.sleep(random.nextInt(1000, 2000));
                future.complete(name);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
    }

    public Future<String> getFastest(){
        CompletableFuture<String> futures = new CompletableFuture<>();

        execute(futures, "Thread-1");
        execute(futures, "Thread-2");
        execute(futures, "Thread-3");

        return futures;
    }

    @Test
    void testFastest() throws ExecutionException, InterruptedException {
        System.out.println(getFastest().get());
    }

    @Test
    void testCompletionStage() throws ExecutionException, InterruptedException {
        CompletableFuture<String> futures = getValue();

        CompletableFuture<String[]> value = futures.thenApply(String::toUpperCase)
                .thenApply(string -> string.split(" "));

        for (var future : value.get()){
            System.out.println(future);
        }
    }
}
