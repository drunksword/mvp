import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shitao on 2018/7/7.
 */
public class HorseRace {
    final int FINISH_LINE = 40;
    private List<Horse> horses = new ArrayList<>();
    private CyclicBarrier barrier;
    private ExecutorService executor = Executors.newCachedThreadPool();
    public HorseRace(int nHorses, int pause){
        barrier = new CyclicBarrier(nHorses, () -> {
            StringBuilder builder= new StringBuilder();
            for(int i=0;i<FINISH_LINE;i++){
                builder.append("=");
            }
            System.out.println(builder.toString());
            for(Horse horse : horses){
                System.out.println(horse);
            }
            System.out.println(builder.toString());

            for(Horse horse : horses){
                if(horse.getRide() >= FINISH_LINE){
                    System.out.println(horse.getId() + " won!");
                    executor.shutdownNow();
                    return;
                }
            }

            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                System.out.println("barrier action sleep interrupted");
            }
        });
        for(int i=0;i<nHorses;i++){
            Horse horse = new Horse(barrier);
            horses.add(horse);
            executor.execute(horse);
        }
    }

    public static void main (String... args){
        new HorseRace(7, 500);
    }
}


class Horse implements Runnable{
    private static int count = 0;
    private int id;
    private CyclicBarrier barrier;
    private Random rand = new Random(new Random().nextInt());
    private int ride = 0;
    public Horse(CyclicBarrier barrier){
        id = count++;
        this.barrier = barrier;
    }

    public int getRide(){
        return ride;
    }
    public int getId(){
        return id;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                ride += rand.nextInt(3) + 1;
                barrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<ride;i++){
            builder.append("*");
        }
        builder.append("    " + id);
        return builder.toString();
    }
}


