//Basic implementation of Alley class (skeleton)
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 25, 2021
package man3;
public class BasicAlley extends Alley {

    int up;
    int down;
    boolean dir;
    boolean UP = true;
    boolean DOWN = false;

    BasicAlley() {
        this.up = 0;
        this.down = 0;
        this.dir = UP;
    }

    @Override
    /* Block until car no. may enter alley */
    public synchronized void enter(int no) throws InterruptedException {
        // 1234 goes down
        if(no > 4) {
            // spurious wake ups
            while(dir==DOWN){
                wait();
            }
            up++;
        } else {
            while(dir==UP){
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
                dir = DOWN;
                System.out.println("going"+dir);
                notifyAll();
            }
        } else {
            down--;
            if(down==0){
                dir = UP;
                System.out.println("going"+dir);
                notifyAll();
            }
        }
    }

}
