package exam;

import lombok.Setter;
import lombok.SneakyThrows;

/**
 * 建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC
 *
 * @author gongjing
 * @date 2020-08-30
 */
public class ThreeThread {

    @Setter
    static class PrintThread extends Thread {
        private String letter;

        private Thread next;

        public PrintThread(String letter) {
            this.letter = letter;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (this){
                    wait();
                    System.out.println(letter);
                }
                synchronized (next){
                    next.notify();
                }
            }
        }

    }

    public static void main(String... args) throws InterruptedException {
        PrintThread aThread = new PrintThread("A");
        PrintThread bThread = new PrintThread("B");
        PrintThread cThread = new PrintThread("C");

        aThread.setNext(bThread);
        bThread.setNext(cThread);
        cThread.setNext(aThread);

        aThread.start();
        bThread.start();
        cThread.start();

        Thread.sleep(10);
        synchronized (aThread){
            aThread.notify();
        }

    }
}
