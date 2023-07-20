package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {
    @Test
    void arrayQueue() throws InterruptedException {
        var queue = new ArrayBlockingQueue<String>(5);
        var thread = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 10; i++){
            thread.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Finish Adding");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        thread.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void linkedQueue() throws InterruptedException {
        var queue = new LinkedBlockingQueue<String>();
        var thread = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 10; i++){
            thread.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Finish Adding");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        thread.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void priorityQueue() throws InterruptedException {
        var queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
        var thread = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 10; i++){
            final int data = i;
            thread.execute(() -> {
                queue.put(data);
                System.out.println("Finish Adding " + data);
            });
        }

        thread.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void delayedQueue() throws InterruptedException {
        var queue = new DelayQueue<ScheduledFuture<String>>();
        var thread = Executors.newFixedThreadPool(10);
        var scheduled = Executors.newScheduledThreadPool(10);

        for(int i = 1; i <= 10; i++){
            final var task = i;
            queue.put(scheduled.schedule(() -> "Data-"+task, task, TimeUnit.SECONDS));
        }

        thread.execute(() -> {
            while (true){
                try {
                    System.out.println(queue.take().get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void synchronousQueue() throws InterruptedException {
        var queue = new SynchronousQueue<String>();
        var thread = Executors.newFixedThreadPool(15);

        for(int i = 0; i < 10; i++){
            final int data = i;
            thread.execute(() -> {
                try {
                    queue.put("Data-"+data);
                    System.out.println("Finish Adding " + data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        thread.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void blockingDeque() throws InterruptedException {
        var queue = new LinkedBlockingDeque<String>();
        var thread = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 10; i++){
            final var data = i;
            try {
                queue.putLast("Data-"+data);
                System.out.println("Finish Adding " + data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        thread.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(queue.takeLast());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void transferQueue() throws InterruptedException {
        var queue = new LinkedTransferQueue<String>();
        var thread = Executors.newFixedThreadPool(15);

        for(int i = 0; i < 10; i++){
            final var data = i;
            thread.execute(() -> {
                try {
                    queue.transfer("Data-"+data);
                    System.out.println("Finish Adding " + data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        thread.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.awaitTermination(1, TimeUnit.MINUTES);
    }
}
