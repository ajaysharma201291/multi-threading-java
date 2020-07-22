package semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// semaphore is object which maintains count also called available permits
// semaphore acquire can work on different threads as different to lock work on same thread to lock
// and unlock it
public class SemaphoreUse {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        //it increase number of available semaphores
        semaphore.release();

        //it decreases the number of available permits and waits till someone releases the permits
        semaphore.acquire();

        System.out.println("Available permits : " + semaphore.availablePermits());



        ExecutorService executorService=Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}

class Connection {
    private static Connection instance = new Connection();

    private Semaphore semaphore = new Semaphore(10,true);

    private int connections=0;

    private Connection() {

    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect(){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        }finally {
            semaphore.release();
        }
    }

    public void doConnect(){

        synchronized (this){
            connections++;
            System.out.println("Current connection count is : "+connections);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this){
            connections--;
        }

    }
}
