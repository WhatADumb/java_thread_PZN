package study.java.thread.model;

public class UserService {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void setUser(String user){
        threadLocal.set(user);
    }

    public void doAction(){
        System.out.println("User: "+threadLocal.get());
    }
}
