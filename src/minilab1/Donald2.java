package minilab1;

public class Donald2 {

    static class P implements Runnable{

        private String name;
        private long startTime;
        public P(String n) {
            name = n;
        }
        private String getTimeDiff() {
            long diff = this.startTime-System.currentTimeMillis();
            return ""+diff;
        }
        public void run() {
            try {
                for (int i = 0; i <10 ; i++) {
                    System.out.println("  I'm "+name+" "+this.getTimeDiff());
                    Thread.sleep(500);
                }
            } catch (Exception e) {System.out.println(name+": "+e);}
        }
    }

    public static void main(String[] argv) {
        try {
            P p1 = new P("Huey!");
            P p2 = new P("    Dewey!");
            P p3 = new P("        Louie!");

            System.out.println();
            System.out.println("Hi kids, who is who?");

//            p1.run();
//            p2.run();
//            p3.run();
            Thread t1 = new Thread(p1);
            Thread t2 = new Thread(p2);
            Thread t3 = new Thread(p3);
            t1.start();t2.start();t3.start();
            t1.join();t2.join();t3.join();

            System.out.println();
            System.out.println("Enough, thanks!");
            System.out.println();

        } catch (Exception e) {System.out.println("main: "+e);}
    }

}


