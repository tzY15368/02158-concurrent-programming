//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;

class SafeBarrier extends Barrier {
    int arrived = 0;
    boolean active = false;
    boolean canGo = false;

    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if (!active)
            return;
        if (no == 0) {
            cd.println("car 0 is synced");
        }

        // protect agianst fast cars
        while (canGo && active) {
            wait();
        }

        arrived++;
        if (arrived < 9) {
            while (canGo != true && active) {
                wait();
            }
        } else {
            canGo = true;
            notifyAll();
        }

        arrived--;
        if (arrived == 0) {
            canGo = false;
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
     * @Override // May be (ab)used for robustness testing public void set(int k) {
     * }
     */

}
