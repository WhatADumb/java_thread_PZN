package study.java.thread;

public class DaemonApp {
    public static void main(String[] args) throws InterruptedException {
        Runnable run = () -> {
            try {
                Thread.sleep(7_000);
                System.out.println("Hello, World!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(run);
        thread.setDaemon(true);
        thread.start();

        System.out.println("Program Finished");
    }
}
