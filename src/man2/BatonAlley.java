//Skeleton implementation of an Alley class  using passing-the-baton
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Sep 29, 2021
package man2;
public class BatonAlley extends Alley {

    // Implementation of the passing the baton model for safe concurrency

    int up = 0, down = 0 , downQueueLen = 0 , upQueueLen = 0;
    Semaphore upSem = new Semaphore(0), downSem = new Semaphore(0), eSem = new Semaphore(1);
    protected BatonAlley() {
        upSem = new Semaphore(0);
        downSem = new Semaphore(0);
        eSem = new Semaphore(1);
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        if (no < 5) {
            eSem.P();
            if(down > 0) {
                upQueueLen++;
                eSem.V();
                upSem.P();
            }
            up++;
            if(upQueueLen>0){
                upQueueLen--;
                upSem.V();
            } else {
                eSem.V();
            }
        } else {
            if(up>0){
                downQueueLen++;
                eSem.V();
                downSem.P();
            }
            down++;
            if(downQueueLen>0){
                downQueueLen--;
                downSem.V();
            } else {
                eSem.V();
            }
        }

     }

    /* Register that car no. has left the alley */
    public void leave(int no) {
        if (no < 5) {
            up--;
            if(up==0 && downQueueLen > 0){
                downQueueLen--;
                downSem.V();
            } else {
                eSem.V();
            }
        } else {
            if(down==0 && upQueueLen >0 ){
                upQueueLen--;
                upSem.V();
            } else {
                eSem.V();
            }
        }
    }

}
