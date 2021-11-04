//Implementation of a basic Barrier class supporting car removal (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;
class RemBarrier extends DynamicBarrier {
    
    public RemBarrier(CarDisplayI cd) {

        super(cd);
    }

    public void removeCar(){
        if(this.thres>=1){
            this.set(this.thres-1);
        } else {
            this.cd.println("no more cars to remove");
        }
    }

    public void restoreCar(){
        if(this.thres <9){
            this.set(this.thres+1);
        } else {
            this.cd.println("no more slots to restore");
        }
    }

    /* Add further methods as needed */
   
    
}
