package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapTest {
    @Test
    void map() throws InterruptedException {
        var countDown = new CountDownLatch(100);

        var map = new ConcurrentHashMap<Integer, String>();
        var thread = Executors.newFixedThreadPool(100);

        for(int i = 0; i < 100; i++){
            final var data = i;
            thread.execute(() -> {
                try {
                    Thread.sleep(1000);
                    map.putIfAbsent(data, "Data-"+data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    countDown.countDown();
                }
            });
        }

        thread.execute(() -> {
            try {
                countDown.await();
                map.forEach((integer, string) -> System.out.println(integer + ": " + string));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void collectionConvert() {
        Set<String> dataSet = Set.of("Alpha", "Omega" ,"Beta");
        Set<String> threadSet = Collections.synchronizedSet(dataSet);

        threadSet.forEach(System.out::println);
    }
}
