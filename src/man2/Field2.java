package man2;

import man2.Pos;
import man2.Semaphore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Field2 {

    private Map<Pos, Semaphore> currentPos;

    public Field2() {
        System.out.println("initiated field");
        currentPos = new ConcurrentHashMap<>();
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