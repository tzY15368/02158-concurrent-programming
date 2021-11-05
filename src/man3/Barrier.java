//Dummy implementation of Barrier class
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Oct 28, 2021
package man3;
public abstract class Barrier {
    
    CarDisplayI cd;
    
    protected Barrier(CarDisplayI cd) { 
        this.cd = cd;
    }
       
    public static Barrier create(CarDisplayI cd) {
        Barrier b = new StickyBarrier(cd);//SafeBarrier(cd);//NaiveBarrier(cd);//DynamicBarrier(cd);
        String msg = "using barrier: "+b.getClass();
        cd.println(msg);
        return b;
    }

    public void sync(int no) throws InterruptedException { 
        
    };
    
    public void on() { 
        cd.println("Barrier On not implemented in this version");
    };
    
    public void off() { 
        cd.println("Barrier Off not implemented in this version");
    };
    
    public void set(int k) {
        cd.println("Barrier threshold setting not implemented in this version");
        // This sleep is solely for illustrating how blocking affects the GUI
        try { Thread.sleep(3000); } catch (InterruptedException e) { }
    }
    
}