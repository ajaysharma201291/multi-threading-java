package threadsynchronization;

public class LowLevelSynchronization {
    public static void main(String[] args) {
        final ProducerConsumer producerConsumer=new ProducerConsumer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.produce2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.consume2();
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
    }
}
