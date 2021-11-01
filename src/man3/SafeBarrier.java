//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;
class SafeBarrier extends Barrier {
    int arrived = 0;
    boolean active = false;
    boolean canGo = true;
    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if(!active)return;
        arrived++;
        if(arrived < 8){
            canGo = false;
            while(canGo==false){
                wait();
            }
        } else {

            arrived =  0;
            canGo = true;
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
        this.arrived = 0;
        notifyAll();
    }

/*    
    @Override
    // May be (ab)used for robustness testing
    public void set(int k) {
    }
*/
    
}
