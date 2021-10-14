class Counter {
    int x = 0;

    public synchronized void incr() {
        x++;
    }

    public synchronized int read() {
        return x;
    }

}


class P extends Thread {
    Counter counter;

    public P(Counter c) {
        counter = c;
    }

    public void run() {
        for (int i = 0; i <10000000 ; i++) {
            counter.incr();
        }
    }
}

public class MonitorCounting {

    public static void main(String[] argv) {
        Counter c = new Counter();
        Thread P1 = new P(c);
        Thread P2 = new P(c);
        System.out.println("Starting with counter = " + c.read());
        P1.start();
        P2.start();
        try {P1.join();} catch (Exception e) {}
        try {P2.join();} catch (Exception e) {}
        System.out.println("Ending with   counter = " + c.read());
    }

}