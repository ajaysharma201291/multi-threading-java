package threadsynchronization;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// use to access variables in multiple threads without worrying for synchronization
// it lets thread tho wait until lathce reaches zero
public class CountdownLatcheUse {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Processor2(latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed....");
    }
}

class Processor2 implements Runnable {
    private CountDownLatch latch;

    public Processor2(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started....");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown();
    }
}
