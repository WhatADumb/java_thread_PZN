package study.java.thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {
    @Test
    void testThread() {
        String nameThread = Thread.currentThread().getName();
        System.out.println(nameThread);
    }

    @Test
    void testCreate() {
        Runnable run = () -> System.out.println("Hello, World!");

        Thread thread = new Thread(run);
        thread.start();
    }

    @Test
    void testSleep() throws InterruptedException {
        Runnable run = () -> {
            try {
                Thread.sleep(2_000);
                System.out.println("Hello, World!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(run);
        thread.start();
        Thread.sleep(2_500);

        System.out.println("Program Finished");
    }

    @Test
    void testJoin() throws InterruptedException {
        Runnable run = () -> {
            try {
                Thread.sleep(2_000);
                System.out.println("Hello, World!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(run);
        thread.start();
        System.out.println("Waiting Program");
        thread.join();

        System.out.println("Program Finished");
    }

    @Test
    void testInterruptWrong() throws InterruptedException {
        Runnable run = () -> {
            for(int i = 0; i < 10; i++){
                System.out.println("Task: " + i);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(run);
        thread.start();
        System.out.println("Waiting Program");
        Thread.sleep(5_000);
        thread.interrupt();
        thread.join();
        System.out.println("Program Finished");
    }

    @Test
    void testInterruptCorrect() throws InterruptedException {
        Runnable run = () -> {
            for(int i = 0; i < 10; i++){
                System.out.println("Task: " + i);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        Thread thread = new Thread(run);
        thread.start();
        System.out.println("Waiting Program");
        Thread.sleep(5_000);
        thread.interrupt();
        thread.join();
        System.out.println("Program Finished");
    }

    @Test
    void testInterruptManual() throws InterruptedException {
        Runnable run = () -> {
            for(int i = 0; i < 10; i++){
                if(Thread.interrupted()){
                    break;
                }
                System.out.println("Task: " + i);
            }
        };

        Thread thread = new Thread(run);
        thread.start();
        System.out.println("Waiting Program");
        Thread.sleep(5_000);
        thread.interrupt();
        thread.join();
        System.out.println("Program Finished");
    }

    @Test
    void testName() {
        Thread thread = new Thread(() -> {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
        });
        thread.setName("CustomName-1");
        thread.start();
    }

    @Test
    void testState() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getState());
            System.out.println("Thread Name: " + Thread.currentThread().getName());
        });
        System.out.println(thread.getState());
        thread.setName("CustomName-1");
        thread.start();
    }
}
