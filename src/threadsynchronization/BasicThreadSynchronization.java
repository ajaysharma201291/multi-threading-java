package threadsynchronization;

import java.util.Scanner;

class Processor extends Thread{

    private volatile boolean running=true;//now guaranteed to work on all systems without volatile may not work
    //volatile prevents thread from caching the variables

    @Override
    public void run() {
        while (running){
            System.out.println("Hello");

            try {
                Thread.sleep(100);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    public void shutdown(){
        running=false;
    }
}

public class BasicThreadSynchronization {
    public static void main(String[] args) {
        Processor p=new Processor();

        p.start();

        System.out.println("Press enter to shut down");
        Scanner sc=new Scanner(System.in);
        sc.nextLine();

        p.shutdown();

    }
}
