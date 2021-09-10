package minilab1;

public class Donald {

    static class P extends Thread{

        private String name;
        private long startTime;
        public P(String n) {
            name = n;
            startTime = System.currentTimeMillis();
        }
        private String getTimeDiff() {
            long diff = this.startTime-System.currentTimeMillis();
            return ""+diff;
        }
        public void run() {
            try {
                for (int i = 0; i <5 ; i++) {
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

            p1.start();
            p2.start();
            p3.start();
            p1.join();
            p2.join();
            p3.join();
            System.out.println();
            System.out.println("Enough, thanks!");
            System.out.println();

        } catch (Exception e) {System.out.println("main: "+e);}
    }

}


