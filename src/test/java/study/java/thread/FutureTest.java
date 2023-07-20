package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {
    @Test
    void testCreate() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(2000);
            return "Hello, World!";
        };

        Future<String> future = service.submit(callable);

        while (!future.isDone()){
            System.out.println("Waiting Finished");
            Thread.sleep(1000);
        }

        System.out.println(future.get());
    }

    @Test
    void testCancel() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello, World!";
        };

        Future<String> future = service.submit(callable);

        System.out.println("Running Future");
        Thread.sleep(2000);
        future.cancel(true);

        System.out.println("Status Cancel: " + future.isCancelled());
        System.out.println(future.get());
        System.out.println("Future Finish");
    }

    @Test
    void testInvokeAll() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Callable<String>> callables = IntStream.range(1,11).mapToObj(value -> (Callable<String>) () -> {
            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).collect(Collectors.toList());

        List<Future<String>> futures = service.invokeAll(callables);

        for(var future : futures){
            System.out.println(future.get());
        }
    }

    @Test
    void testInvokeAny() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Callable<String>> callables = IntStream.range(1,11).mapToObj(value -> new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(value * 500L);
                return String.valueOf(value);
            }
        }).collect(Collectors.toList());

        String future = service.invokeAny(callables);
        System.out.println(future);
    }
}
