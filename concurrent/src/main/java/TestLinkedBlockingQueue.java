import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by shitao on 2018/7/15.
 */
public class TestLinkedBlockingQueue {

    @Test
    public void main() throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);
//        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("wake up");
            for(int i=0;i<10;i++){
                try {
//                    Thread.sleep(1000);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for(int i=10;i<20;i++){
                System.out.println("put " + i);
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        for(int i=0;i<10;i++){
            System.out.println("put " + i);
            queue.put(i);
        }
    }
}
