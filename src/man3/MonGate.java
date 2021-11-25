//Monitor implementation of Gate (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;

public class MonGate extends Gate {

    boolean isOpen = false;
    public synchronized void pass() throws InterruptedException {
        while(!isOpen){
            wait();
        }
        
    }

    public synchronized void open() {
        isOpen = true;
        notifyAll();
    }

    public synchronized void close() {
        isOpen = false;
    }

}

