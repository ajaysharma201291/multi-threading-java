package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Thread pool is like number of worker in a factory. They will work on given task and when that task is
// completed they will switch to next task

// Executor service will use its managerial task to handle task management in threads
public class ThreadPoolUse {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Processor(i));
        }

        executorService.shutdown();

        System.out.println("All tasks submitted.");

        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed.");
    }
}

class Processor implements Runnable{

    private  int id;

    Processor(int id){
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println("Starting : "+id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Ending : "+id);
    }
}
