package interruptingThread;

import java.util.Random;

public class InterruptingThreadSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting......");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                for (int i = 0; i < 1E8; i++) {

                    // check interrupted thread
                    /*
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted");
                        break;
                    }*/

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted");
                        e.printStackTrace();
                        break;
                    }

                    Math.sin(random.nextDouble());
                }
            }
        });

        t1.start();

        Thread.sleep(500);

        // This method on thread doesn't stop the thread but just set a flag on thread that it is
        // interrupted
        t1.interrupt();

        t1.join();

        System.out.println("Finished......");
    }
}
