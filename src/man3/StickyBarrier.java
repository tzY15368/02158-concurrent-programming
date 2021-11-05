//Implementation of sticky Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class StickyBarrier extends Barrier {
    private int capacity = 9;
    private int arrived = 0;
    private boolean active = false;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition cond = lock.newCondition();

    public StickyBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {
        try {
            lock.lock();
            if (!this.active)
                return;
            if (arrived < this.capacity) {
                this.arrived++;
                this.cd.println("arrived=" + arrived);
                cond.await();
            } else {
                cond.signal();
                cond.await();
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void on() {
        lock.lock();
        this.active = true;
        lock.unlock();
    }

    @Override
    public void off() {
        lock.lock();
        this.active = false;
        this.arrived = 0;
        cond.signalAll();
        lock.unlock();
    }

    @Override
    /* Set barrier capacity */
    public void set(int k) {
        lock.lock();
        this.cd.println("setting cap = " + k);
        if (this.active && this.arrived > k) {
            this.cd.println("arrived=" + this.arrived);
            for (int i = 0; i < this.arrived - k; i++) {
                this.cond.signal();
                this.arrived--;
            }
        }
        this.capacity = k;
        lock.unlock();
    }

}
