package man2;

import java.util.HashMap;
import java.util.Map;

//Prototype implementation of Field class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2021

//Hans Henrik Lovengreen     Sep 29, 2021

public class Field {
    // ensure immutability
    static private Semaphore[][] currentPos;

    public Field() {
        System.out.println("initiated field");
        currentPos = new Semaphore[11][12];
        for(int i=0;i<11;i++)
        {
            for(int j=0;j<12;j++)
            {
                currentPos[i][j]=new Semaphore(1);
            }
        }
    }

    /* Block until car no. may safely enter tile at pos */
    public void enter(int no, Pos pos) throws InterruptedException {
        currentPos[pos.row][pos.col].P();
    }

    /* Release tile at position pos */
    public void leave(Pos pos) throws InterruptedException {
        currentPos[pos.row][pos.col].V();
    }

}
