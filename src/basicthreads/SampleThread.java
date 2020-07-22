package basicthreads;

class Thread1 extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello " + i);
        }

        try {
            Thread.sleep(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Thread2 implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello " + i);
        }

        try {
            Thread.sleep(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

public class SampleThread {
    public static void main(String[] args) {
        Thread1 t = new Thread1();
        t.start();

        Thread2 t2 = new Thread2();
        Thread t3 = new Thread(t2);
        t3.start();

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Hello " + i);
                }

                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        t4.start();
    }
}
