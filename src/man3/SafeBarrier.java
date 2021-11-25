//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;
class SafeBarrier extends Barrier {
    int arrived = 0;
    boolean active = false;
    boolean shouldStop = false;
    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if(!active)return;

        while (active && shouldStop) {
            wait();
        }
        arrived++;

        if (arrived == 9) {
            shouldStop = true;
            notifyAll();
        }

        while (active && !shouldStop) {
            wait();
        }
        arrived--;

        if (arrived == 0) {
            shouldStop = false;
            notifyAll();
        }
    }

    @Override
    public synchronized void on() {
        this.active = true;
    }

    @Override
    public synchronized void off() {
        this.active = false;
        notifyAll();
    }

/*    
    @Override
    // May be (ab)used for robustness testing
    public void set(int k) {
    }
*/
    
}
