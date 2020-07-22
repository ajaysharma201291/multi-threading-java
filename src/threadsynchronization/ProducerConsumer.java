package threadsynchronization;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ProducerConsumer {

    private LinkedList<Integer> list=new LinkedList<>();
    private final int LIMIT=10;
    private Object lock=new Object();

    void produce() throws InterruptedException {
        synchronized (this){
            System.out.println("Producer thread running............");
            wait();
            System.out.println("Resumed......");
        }
    }

    void produce2() throws InterruptedException {
        int value=0;
        while (true){
            synchronized (lock) {
                while (list.size()==LIMIT){
                   lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Thread.sleep(2000);

        synchronized (this){
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            notify();
            Thread.sleep(5000);
        }
    }

    void consume2() throws InterruptedException {

        Random random = new Random();

        while (true) {
            synchronized (lock) {

                while (list.size()==0){
                    lock.wait();
                }

                System.out.print("List size is : " + list.size());
                int value = list.removeFirst();
                System.out.println(" , value is : " + value);
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000));
        }
    }
}
