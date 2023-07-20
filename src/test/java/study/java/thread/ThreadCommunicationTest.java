package study.java.thread;

import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {
    private String message = null;
    private final Object lock = new Object();

    @Test
    void testManual() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
           while (message == null){

           }
            System.out.println(message);
        });

        Thread thread2 = new Thread(() -> {
            message = "Hello, World!";
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    void testWaitNotify() throws InterruptedException {



        Thread thread1 = new Thread(() -> {
            synchronized (lock){
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock){
                message = "Hello, World!";
                lock.notify();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    void testWaitNotifyAll() throws InterruptedException {



        Thread thread1 = new Thread(() -> {
            synchronized (lock){
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            synchronized (lock){
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock){
                message = "Hello, World!";
                lock.notifyAll();
            }
        });

        thread1.start();
        thread3.start();
        thread2.start();

        thread1.join();
        thread3.join();
        thread2.join();
    }
}
