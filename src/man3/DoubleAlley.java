//Implementation of Alley class with inner alley (skeleton)
//CP Lab 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 25, 2021
package man3;
public class DoubleAlley extends Alley {
   
    DoubleAlley() {
    }

    @Override
    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
    }

    @Override
    /* Register that car no. has left the alley */
    public void leave(int no) {
    }
    
    @Override
    /* Register that car no. has left the inner alley */
    public void leaveInner(int no) {
    }

}
