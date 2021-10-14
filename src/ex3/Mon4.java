package ex3;

public class Mon4{

    private boolean shouldWakeUp = false;

    public synchronized void sleep() throws InterruptedException {
        wait();
    }

    public synchronized void wakeup(){
        shouldWakeUp = true;
        notify();
    }
}


