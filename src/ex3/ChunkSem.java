package ex3;

public class ChunkSem {
    private int s = 0;
    private final int M = 5;

    public synchronized void P() throws InterruptedException {
        while(s==0){
            wait();
        }
        s--;
    }
    // add a queue, 只有在P完M次后才批量notify？
    // 实际上是P一个Queue，V一个Queue
    // 才能避免惊群效应？
    public synchronized void V(){

        s += M;
        // for i in range M: notify()

    }

}
