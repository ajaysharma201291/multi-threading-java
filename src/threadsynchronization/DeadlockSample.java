package threadsynchronization;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSample {
    public static void main(String[] args) {
        final Runner2 runner = new Runner2();

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

class Runner2 {

    private Account acc1 = new Account();
    private Account acc2 = new Account();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    // solving order of lock causing deadlock problem
    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
            //Acquire locks

            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                gotFirstLock=firstLock.tryLock();
                gotSecondLock=secondLock.tryLock();
            }
            finally {
                if(gotFirstLock && gotSecondLock){
                    return;
                }

                if (gotFirstLock){
                    firstLock.unlock();
                }

                if(gotSecondLock){
                    secondLock.unlock();
                }
            }

            //locks not acquired
            Thread.sleep(1);
        }


    }

    public void firstThread() throws InterruptedException {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {

            //lock1.lock();
            //lock2.lock();

            acquireLocks(lock1, lock2);

            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }

        }

    }

    public void secondThread() throws InterruptedException {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            /* reordering like below result in deadlock due to resource being held by another thread
            lock2.lock();
            lock1.lock();
*/

            //lock1.lock();
            //lock2.lock();

            acquireLocks(lock2, lock1);

            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance is : " + acc1.getBalance());
        System.out.println("Account 2 balance is : " + acc2.getBalance());
        System.out.println("Total balance is : " + (acc2.getBalance() + acc1.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}
