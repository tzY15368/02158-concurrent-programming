//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;
class DynamicBarrier extends Barrier {
    int arrived = 0;
    boolean active = false;
    boolean canGo = true;
    int thres = 9;
    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if(!active)return;
        arrived++;
        if(arrived < this.thres){
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
        this.canGo = true;
        notifyAll();
    }


    @Override
    // May be (ab)used for robustness testing
    public synchronized void set(int k) {
        this.cd.println("thres will be set to "+k);
        if(k<=this.thres){
            this.thres = k;
            this.canGo = true;
            this.arrived = 0;
            notifyAll();
            this.cd.println("k <= thres, releasing all cars");
            return;
        } else {
            System.out.println("k > ");
            while(this.arrived!=0){
                try {
                    this.cd.println("started blocking");
                    wait();
                } catch (InterruptedException e){

                }
            }
            this.cd.println("out of blocking");
            this.thres = k;
        }
    }


}
