package callableandfuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

// Callable and Future allow to return result from threads and also allow threads to throw exception
public class CallableAndFutureUse {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//
//                Random random = new Random();
//
//                int duration = random.nextInt(4000);
//
//                System.out.println("Starting......");
//
//                try {
//                    Thread.sleep(duration);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("Finished.....");
//
//            }
//        });

//        Future<Integer> future = executorService.submit(new Callable<Integer>() {
        Future<?> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();

                int duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IOException("Sleeping for too longer duration");
                }

                System.out.println("Starting......");

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished.....");

                return duration;
            }
        });

        executorService.shutdown();

        try {
            System.out.println("Duration Result is : " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

            IOException ex= (IOException) e.getCause();

            System.out.println(ex.getMessage());
        }
    }
}
