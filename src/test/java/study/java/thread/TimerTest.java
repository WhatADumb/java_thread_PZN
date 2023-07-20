package study.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    @Test
    void testDelayed() throws InterruptedException {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer time = new Timer();
        time.schedule(task, 2_000L);

        Thread.sleep(2_500L);
    }

    @Test
    void testPeriodic() throws InterruptedException {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Periodic Job");
            }
        };

        Timer time = new Timer();
        time.schedule(task, 2_000L, 1_000L);

        Thread.sleep(10_000L);
    }
}
