//Monitor implementation of Gate (skeleton)
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 25, 2021
package man3;

public class MonGate extends Gate {

    boolean isOpen = false;
    public synchronized void pass() throws InterruptedException {
        if(isOpen==false){
            wait();
        }
        notifyAll();
    }

    public synchronized void open() {
        if(isOpen == false){
            isOpen = true;
            notifyAll();
        }
    }

    public synchronized void close() {
        if(isOpen==true){
            try {
                wait();
            } catch (InterruptedException e){

            }
            isOpen = false;
        }
    }

}
