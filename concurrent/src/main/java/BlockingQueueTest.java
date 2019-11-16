import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast {
    public enum Status {DRY, BUTTERED, JAMMED}

    private Status status = Status.DRY;
    private final int id;
    public Toast(int idn){
        id = idn;
    }
    public void butter(){
        status = Status.BUTTERED;
    }
    public void jam(){
        status = Status.JAMMED;
    }

    public Status getStatus(){return status;}
    public int getId(){return id;}
    public String toString(){
        return "Toast" + id + ":" + status;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}


class Toaster implements Runnable{
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);
    public Toaster(ToastQueue toastQueue){
        this.toastQueue = toastQueue;
    }

    @Override
    public void run(){
        try {
            while(!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                Toast t = new Toast(count++);
                BlockingQueueTest.print(t);
                toastQueue.put(t);
            }
        } catch (InterruptedException e) {
            BlockingQueueTest.print("Toaster interrupted!");
        }
        BlockingQueueTest.print("Toaster off!");
    }
}

class Bufferer implements Runnable{
    private ToastQueue toastQueue, bufferedQueue;
    public Bufferer(ToastQueue toastQueue, ToastQueue bufferedQueue){
        this.toastQueue = toastQueue;
        this.bufferedQueue = bufferedQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Toast t = toastQueue.take();
                t.butter();
                BlockingQueueTest.print(t);
                bufferedQueue.put(t);
            }
        } catch (InterruptedException e) {
            BlockingQueueTest.print("Bufferer interrupted!");
        }
        BlockingQueueTest.print("Bufferer off!");
    }
}

class Jammer implements Runnable{
    private ToastQueue bufferedQueue, finishedQueue;
    public Jammer(ToastQueue bufferedQueue, ToastQueue finishedQueue){
        this.bufferedQueue = bufferedQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Toast t = bufferedQueue.take();
                t.jam();
                BlockingQueueTest.print(t);
                finishedQueue.put(t);
            }
        } catch (InterruptedException e) {
            BlockingQueueTest.print("Jammer interrupted!");
        }
        BlockingQueueTest.print("Jammer off!");
    }
}

class Eater implements Runnable{
    private ToastQueue finishedQueue;
    private int count = 0;
    public Eater(ToastQueue finishedQueue){
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Toast t = finishedQueue.take();
                if(t.getId() != count++ || t.getStatus() != Toast.Status.JAMMED){
                    BlockingQueueTest.print("error" + t);
                    System.exit(-1);
                }
                BlockingQueueTest.print(t + ">> is eaten;");
            }
        } catch (InterruptedException e) {
            BlockingQueueTest.print("Eater interrupted!");
        }
        BlockingQueueTest.print("Eater off!");
    }
}

public class BlockingQueueTest {
    public static void print(Object obj){
        System.out.println(obj.toString());
    }
    public static void main(String[] args) throws InterruptedException {
        ToastQueue toastQueue = new ToastQueue(), butteredQueue = new ToastQueue(), finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(toastQueue));
        exec.execute(new Bufferer(toastQueue, butteredQueue));
        exec.execute(new Jammer(butteredQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.MILLISECONDS.sleep(5000);
        exec.shutdownNow();
    }
}
