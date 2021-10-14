package ex3;

class Procs {
    private int s = 0;
    public synchronized void A() throws InterruptedException {
        System.out.println("A");
        Thread.sleep(1000);
        while(s==0){
            wait();
            System.out.println("got notified");
        }
        System.out.println("A end");
    }

    public synchronized void B() throws InterruptedException {
        System.out.println("B");
        Thread.sleep(2000);
        System.out.println("B change s");
        s = 3;
        notify();
        System.out.println("end of B");
    }
}

public class Mon1 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Procs p = new Procs();
        Thread t1 = new Thread(() -> {
            try {
                p.A();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                p.B();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("main exit");
    }
}
