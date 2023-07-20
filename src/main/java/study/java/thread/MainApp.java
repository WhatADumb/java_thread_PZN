package study.java.thread;

public class MainApp {
    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }
}
