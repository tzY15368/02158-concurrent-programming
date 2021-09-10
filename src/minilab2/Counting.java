package minilab2;

public class Counting {

    static final int N = 1000000;

    static volatile int x = 0;

    static class T extends Thread {

        public void run() {
            try {

                for (int i = 0; i < N ; i++) {
                    x++;
                    sleep(1000);
                }

            } catch (java.lang.InterruptedException e){
                System.out.println(e);
            } catch (Exception e){
                return;
            }
        }
    }

    public static void main(String[] argv) {
        try {
            Thread t1 = new T();
            Thread t2 = new T();
            System.out.println("Starting with x = 0");
            t1.start();
            t2.start();
            Thread.sleep(1000);
            t2.interrupt();
            t1.join();
            t2.join();
            System.out.println("Ending with   x = "+x);
        } catch (Exception e) {System.out.println("Counting: "+e);}

    }

}
