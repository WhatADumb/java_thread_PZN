package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinTest {
    @Test
    void create() {
        var forkJoin1 = ForkJoinPool.commonPool(); //using all cores
        var forkJoin2 = new ForkJoinPool(2); //using how many cores selected
        var forkJoin3 = Executors.newWorkStealingPool(); //using all cores
        var forkJoin4 = Executors.newWorkStealingPool(2); //using how many cores selected
    }

    private static class SimpleForkJoinTask extends RecursiveAction{
        List<Integer> integers;

        public SimpleForkJoinTask(List<Integer> integers) {
            this.integers = integers;
        }

        private void doExecute(){
            integers.forEach(integer -> {
                System.out.println(Thread.currentThread().getName() + ": " + integer);
            });
        }

        private void forkCompute(){
            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

            SimpleForkJoinTask forkJoin1 = new SimpleForkJoinTask(integers1);
            SimpleForkJoinTask forkJoin2 = new SimpleForkJoinTask(integers2);

            ForkJoinTask.invokeAll(forkJoin1, forkJoin2);
        }

        @Override
        protected void compute() {
            if (integers.size() <= 5){
                doExecute();
            }else{
                forkCompute();
            }
        }
    }

    private static class TotalForkJoinTask extends RecursiveTask<Long>{

        List<Integer> integers;

        public TotalForkJoinTask(List<Integer> integers) {
            this.integers = integers;
        }

        private Long doExecute(){
            return this.integers.stream().mapToLong(value -> value).peek(value -> {
                System.out.println(Thread.currentThread().getName() + ": " + value);
            }).sum();
        }

        private Long doFork(){
            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

            TotalForkJoinTask task1 = new TotalForkJoinTask(integers1);
            TotalForkJoinTask task2 = new TotalForkJoinTask(integers2);

            ForkJoinTask.invokeAll(task1, task2);

            return task1.join() + task2.join();
        }

        @Override
        protected Long compute() {
            if(integers.size() <= 5){
                return doExecute();
            }else {
                return doFork();
            }
        }
    }

    @Test
    void testRecursiveAction() throws InterruptedException {
        var forkJoin = ForkJoinPool.commonPool();
        var integers = IntStream.range(0, 100).boxed().toList();

        var task = new SimpleForkJoinTask(integers);
        forkJoin.execute(task);

        forkJoin.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void testRecursiveTask() throws ExecutionException, InterruptedException {
        var forkJoin = ForkJoinPool.commonPool();
        var integers = IntStream.range(0, 100).boxed().toList();

        var task = new TotalForkJoinTask(integers);
        ForkJoinTask<Long> valueTask = forkJoin.submit(task);

        Long data = valueTask.get();
        System.out.println(data);

        forkJoin.awaitTermination(1, TimeUnit.MINUTES);
    }
}
