package threadsynchronization;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockUse {
    public static void main(String[] args) {
        final Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

        runner.finished();
    }
}

class Runner {

    private int count = 0;
    // this can lock given thread any number of time and then unlock it same number of time before
    // other thread acquire the lock
    private Lock lock = new ReentrantLock();

    // similar to wait and notify of object class
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("Waiting......");
        condition.await();

        System.out.println("Woken up");

        try {
            System.out.println("increment first thread......");
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key pressed!");

        condition.signal();

        try {
            System.out.println("increment second thread......");
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is : " + count);
    }
}