import java.util.HashMap;

//Prototype implementation of Field class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Sep 29, 2021

public class Field {

    private HashMap<Pos,Semaphore> currentPos;

    public Field() {
        System.out.println("initiated field");
        currentPos = new HashMap<>();
    }

    /* Block until car no. may safely enter tile at pos */
    public void enter(int no, Pos pos) throws InterruptedException {
        if(!currentPos.containsKey(pos)){
            currentPos.put(pos, new Semaphore(1));
        }
        currentPos.get(pos).P(); 

    }

    /* Release tile at position pos */
    public void leave(Pos pos) {
        currentPos.get(pos).V();
    }

}
