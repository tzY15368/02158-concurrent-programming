package lab1;


/*
 * Concurrent Programming Lab 1 (Swing version)
 */

import java.awt.*;

import javax.swing.JTextField;

class Task extends Thread   {

    JTextField tf; // Textfield to be used by this task

    public Task(JTextField t) {
        tf = t;
    }

    void setMyText(final String s) {
        /*
         * Checks if called by GUI thread (= dispatch thread) or not. Although
         * using invokeLater is always sound, calling setText directly is more
         * efficient when called by the GUI thread.
         */
        if (EventQueue.isDispatchThread()) {
            // On GUI thread -- OK to set text directly
            tf.setText(s);
        } else {
            // not on GUI thread -- put update request on event queue
            EventQueue.invokeLater(() -> {
                tf.setText(s);
            });
        }
    }

    public void run() {
        int cols = tf.getColumns();

        boolean useCPU = true; // Set true to consume CPU-cycles

        int basespeed = 5000; // millisecs to do task
        int variation = (useCPU ? 0 : 60); // Speed variation in percent
        boolean localIsInterrupted = false;
        long delay = Math.round(((Math.random() - 0.5) * variation / 50 + 1.0) * basespeed / cols);

        String s = "";

        setMyText(s);
        while (s.length() < cols) {

            if (useCPU) {
                for (int j = 0; j < 5000000 * delay; j++) {
                }
            } else {
                try {
                    Thread.sleep(delay);

                } catch (InterruptedException e) {
                    localIsInterrupted = true;
                }
            }
            localIsInterrupted = this.isInterrupted();
            s += localIsInterrupted?'|':'#';
            cols = localIsInterrupted?-1:cols;
            setMyText(s);
        }
    }
}

public class TaskControl {

    static final int N = 6; // Number of Textfields

    static int h = 0; // Number of 'hello'-s
    static Task localTask;
    static Task[] localTasks = new Task[N];
    public static void main(String[] argv) {
        try {

            // Create window with N JTextFields: (d.tf[0], ... , d.tf[N-1])
            TaskDisplay d = new TaskDisplay("Task Control", N);

            d.println("Type command (x to exit):");

            // Main command interpretation loop
            W: while (true) {

                char c = d.getKey();

                switch (c) {

                    case 'x':
//                        if(localTask!=null && localTask.isAlive()){
//                            d.println("waiting for task to finish");
//                            localTask.join();
//                        }
                        // *dies
                        break W;

                    case 'h':
                        d.println("Hello " + (h++));
                        break;

                    case 't':
                        boolean didRun = false;
                        for(int i=0;i<localTasks.length;i++){
                            if(localTasks[i]==null || ! localTasks[i].isAlive()){
                                localTasks[i] = new Task(d.tf[i]);
                                localTasks[i].start();
                                d.println("started a task in "+i);
                                didRun = true;
                                break;
                            }
                        }
                        if(!didRun){
                            d.println("no available textfields");
                        }
                        break;
                    default:
                        if ((c-'0') >=0 && (c-'0')<N){
                            Task t = localTasks[(c-'0')];
                            if(t!=null && t.isAlive()){
                                d.println(String.format("stopped task %d",(c-'0')));
                                t.interrupt();

                            } else {
                                d.println(String.format("cannot stop task %d",(c-'0')));
                            }
                        } else {

                            d.println("Don't know '" + c + "'");
                        }
                }
            }

            d.println("Program terminates");

            Thread.sleep(500);
            System.exit(0);

        } catch (Exception e) {
            System.out.println("Exception in Task Control: " + e);
            e.printStackTrace();
        }
    }
}
