//Naive implementation of Barrier class
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;

class NaiveBarrier extends Barrier {

    int arrived = 0;
    boolean active = false;

    public NaiveBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {

        if (!active) return;                        // 4
        //if (!active) {Thread.sleep(500); return;}
        //this.cd.println("sleeping 500");
        //Thread.sleep(500);                          
        arrived++;                                  // 5

        synchronized (this) {                       // 6
            System.out.println("arrived:"+arrived); // 7
            if (arrived < 9) {                      // 8
                wait();                             // 9
            } else {
                arrived = 0;
                notifyAll();
            }

        }
    }                                               // 11          <--------- problem

    @Override
    public void on() {
        active = true;                                  // 2
    }

    @Override
    public void off() {
        active = false;                                 // 1
        arrived = 0;                                    // 3
        synchronized (this) {
            notifyAll();                                // 10
        }
    }


    @Override
    // May be (ab)used for robustness testing
    public synchronized void set(int k) {
        notifyAll();
    }    


}
