package threadsynchronization;

public class SynchronizedKeywordUse {
    private int count = 0;

    // using synchronized keyword will fix the different count value issue without it has problem persist
    // using synchronized keyword will make intrinsic lock on common variable by each thread and thus stop
    // the multi thread accessing same variable at the time
    public synchronized void increment() {
        count++;
    }

    public static void main(String[] args) {
        SynchronizedKeywordUse s = new SynchronizedKeywordUse();
        s.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    //count++;
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    //count++;
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // here result may change sometimes even volatile keyword not able to fix the issue
        System.out.println("Count value is : " + count);
    }
}
