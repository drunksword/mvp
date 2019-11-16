import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by shitao on 2018/7/7.
 */
public class CarBuilder {
    public static void main(String... args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        RobotPool pool = new RobotPool();
        CarQueue carQueue = new CarQueue(), finishQueue = new CarQueue();

        exec.execute(new ChassisBuilder(carQueue));
        exec.execute(new Reporter(finishQueue));

        exec.execute(new EngineRobot(pool));
        exec.execute(new DriveTrainRobot(pool));
        exec.execute(new WheelRobot(pool));

        exec.execute(new Assembler(carQueue, finishQueue, pool));

        TimeUnit.MILLISECONDS.sleep(1000);
        exec.shutdownNow();
        System.out.println("finished!");
    }

}

class Car{
    private int id;
    private static int count = 0;

    boolean engine = false, driveTrain = false, wheels = false;
    public Car(){
        id = count++;
    }

    public void addEngine(){engine = true;}
    public void addDriveTrain(){driveTrain = true;}
    public void addWheels(){wheels = true;}

    @Override
    public String toString(){
        return String.format("car%d, engine[%s], driveTrain[%s], wheels:[%s]", id, engine+"", driveTrain+"", wheels+"");
    }

    public int getId() {
        return id;
    }
}

class CarQueue extends LinkedBlockingQueue<Car>{}

class ChassisBuilder implements Runnable{

    CarQueue carQueue;
    public ChassisBuilder (CarQueue carQueue){
        this.carQueue = carQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Car car = new Car();
                carQueue.add(car);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println("ChassicBuild interrupted!");
        }
        System.out.println("chassis builder down!");
    }
}

abstract class Robot implements Runnable{

    protected Assembler assembler;
    private RobotPool robotPool;

    public Robot(RobotPool robotPool){
        this.robotPool = robotPool;
    }

    public synchronized void register(Assembler assembler){
        this.assembler = assembler;
        notifyAll();
    }
    private synchronized void powerDown() throws InterruptedException {
        assembler = null;
        robotPool.release(this);
        wait();
    }

    abstract void performWork();

    @Override
    public void run(){
        try {
            powerDown();
            while(!Thread.interrupted()){
                performWork();
                assembler.barrier().await();
                powerDown();
            }
        } catch (Exception e) {
            System.out.println(this.getClass().getName() + " interrupted");
        }
        System.out.println(this.getClass().getName() + " down!");
    }
}

class EngineRobot extends Robot{

    public EngineRobot(RobotPool robotPool){
        super(robotPool);
    }

    @Override
    void performWork() {
        System.out.println(this + " installing engine");
        assembler.car().addEngine();
    }
}
class DriveTrainRobot extends Robot{

    public DriveTrainRobot(RobotPool robotPool){
        super(robotPool);
    }

    @Override
    void performWork() {
        System.out.println(this + " installing driveTrain");
        assembler.car().addDriveTrain();
    }
}
class WheelRobot extends Robot{

    public WheelRobot(RobotPool robotPool){
        super(robotPool);
    }

    @Override
    void performWork() {
        System.out.println(this + " installing wheels");
        assembler.car().addWheels();
    }
}

class RobotPool {
    Set<Robot> pool = new HashSet<>();

    public synchronized void hire(Class<? extends Robot> clazz, Assembler assembler) throws InterruptedException {
        for(Robot robot : pool){
            if(robot.getClass() == clazz){
                pool.remove(robot);
                robot.register(assembler);
                return;
            }
        }
        wait();
        hire(clazz, assembler);
    }

    public synchronized void release(Robot robot){
        pool.add(robot);
        notifyAll();
    }
}

class Assembler implements Runnable{
    private CarQueue carQueue, finishQueue;
    private RobotPool robotPool;

    private CyclicBarrier barrier = new CyclicBarrier(4);

    public Assembler(CarQueue carQueue, CarQueue finishQueue, RobotPool robotPool){
        this.carQueue = carQueue;
        this.finishQueue = finishQueue;
        this.robotPool = robotPool;
    }

    public CyclicBarrier barrier(){return barrier;}

    private Car car;
    public Car car(){return car;}

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                car = carQueue.take();
                robotPool.hire(EngineRobot.class, this);
                robotPool.hire(DriveTrainRobot.class, this);
                robotPool.hire(WheelRobot.class, this);
                barrier.await();
                System.out.println("assembler is assembling car " + car.getId());
                //do sth assembler
                finishQueue.add(car);
            }
        } catch (Exception e) {
            System.out.println("assembler interrupted!");
        }
        System.out.println("assembler down!");
    }
}

class Reporter implements Runnable{
    private CarQueue finishQueue;

    public Reporter(CarQueue finishQueue){
        this.finishQueue = finishQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Car car = finishQueue.take();
                System.out.println("finished " + car);
            }
        } catch (InterruptedException e) {
            System.out.println("reporter interrupted");
        }
        System.out.println("reporter down!");
    }
}