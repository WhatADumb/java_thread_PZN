package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamParallelTest {
    @Test
    void testParallel() {
        Stream<Integer> integers = IntStream.range(0,100).boxed();
        integers.parallel().forEach(integer -> {
            System.out.println(Thread.currentThread().getName() + ": " + integer);
        });
    }

    @Test
    void testSubmitParallel() {
        ForkJoinPool forkJoin = ForkJoinPool.commonPool();

        ForkJoinTask<?> task = forkJoin.submit(() -> {
            Stream<Integer> integers = IntStream.range(0,100).boxed();

            integers.parallel().forEach(integer -> {
                System.out.println(Thread.currentThread().getName() + ": " + integer);
            });
        });

        task.join();
    }
}
