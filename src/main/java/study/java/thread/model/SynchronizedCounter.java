package study.java.thread.model;

public class SynchronizedCounter {
    private Long value = 0L;

/*
    This is Synchronized Method
    public synchronized void increment(){
        value++;
    }
*/


    //This is Synchronized Statement
    public void increment(){
        synchronized (this){
            value++;
        }
    }

    public Long getValue(){
        return value;
    }
}
