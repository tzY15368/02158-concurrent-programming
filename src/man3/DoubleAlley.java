//Implementation of Alley class with inner alley (skeleton)
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 25, 2021
package man3;

public class DoubleAlley extends Alley {

    int up;
    int down;

    int innerUp;

    DoubleAlley() {
        this.up = 0;
        this.down = 0;
    }

    @Override
    /* Block until car no. may enter alley */
    public synchronized void enter(int no) throws InterruptedException {
        // 1234 goes down
        if (no > 4) {
            // spurious wake ups
            while (down > 0) {
                wait();
            }
            up++;
            innerUp++;
        } else if (no > 2) {
            while (up > 0) {
                wait();
            }
            down++;
        } else if(no > 0) {
            while(innerUp > 0){
                wait();
            }
            down++;
        }
    }

    @Override
    /* Register that car no. has left the alley */
    public synchronized void leave(int no) {
        if (no > 4) {
            up--;
            if(up==0){
                notifyAll();
            }
        } else {
            down--;
            if (down == 0) {
                notifyAll();
            }
        }
    }

    @Override
    /* Register that car no. has left the inner alley */
    public synchronized void leaveInner(int no) {
        innerUp--;
        if(innerUp==0){
            notifyAll();
        }
    }

}
