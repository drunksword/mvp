package restaurant;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**a
 * Created by shitao on 2018/6/24.
 */
public class Restaurant implements Runnable{
    List<Chef> chefs;
    List<Waitor> waitors;
    BlockingQueue<Order> orders = new LinkedBlockingDeque<>();
    ExecutorService exec;

    Random rand = new Random();

    public Restaurant(ExecutorService exec, int chefCnt, int waitorCnt){
        this.exec = exec;
        chefs = new ArrayList<>(chefCnt);
        waitors = new ArrayList<>(waitorCnt);
        for(int i=0;i<chefCnt;i++){
            Chef chef = new Chef(this);
            chefs.add(chef);
            System.out.println(chef + " is ready");
            exec.execute(chef);
        }
        for(int i=0;i<waitorCnt;i++){
            Waitor waitor = new Waitor(this);
            waitors.add(waitor);
            System.out.println(waitor + " is ready");
            exec.execute(waitor);
        }
    }

    public Waitor getRandWaitor(){
        return waitors.get(rand.nextInt(waitors.size()));
    }

    @Override
    public void run() {
        System.out.println("restaurant open");
        try {
            while(!Thread.interrupted()){
                Waitor waitor = getRandWaitor();
                Customer customer = new Customer(waitor);
                System.out.println(customer + " arrive");
                exec.execute(customer);
                Thread.sleep(rand.nextInt(100));
            }
        } catch (InterruptedException e) {
            System.out.println("restaurant interrupted!");
        }
        System.out.println("restaurant close!");
    }

    public static void main(String... args){
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec, 2, 5);
        exec.execute(restaurant);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread main interrupted!");
        }
        exec.shutdownNow();
        System.out.println("Thread main exit!");
    }
}

class Order{
    private static int count = 0;
    final int id = count++;
    @Override
    public String toString(){
        return String.format("order%d(%s, belong customer%d, waitor%d)", id, food, customer.id, waitor.id);
    }
    private Customer customer;
    private Waitor waitor;
    private Food food;

    public Order(Customer customer, Waitor waitor, Food food){
        this.customer = customer;
        this.waitor = waitor;
        this.food = food;
    }
    public Food getFood() {return food;}
    public Waitor getWaitor() {return waitor;}
    public Customer getCustomer(){return customer;}

    public void cooked() {
        waitor.plates.add(new Plate(this, food));
    }
}

class Plate{
    private static int count = 0;
    private final int id = count++;
    @Override
    public String toString(){
        return String.format("plate%d(order%d)", id, order.id);
    }
    private Order order;
    private Food food;

    public Plate(Order order, Food food){
        this.order = order;
        this.food = food;
    }
    public Order getOrder(){return order;}
}

class Customer implements Runnable{
    private static int count = 0;
    final int id = count++;
    @Override
    public String toString(){
        return String.format("customer%d(with waitor%d)", id, waitor.id);
    }

    private Waitor waitor;
    SynchronousQueue<Plate> plates = new SynchronousQueue<>();

    public Customer(Waitor waitor){
        this.waitor = waitor;
    }

    public void deliver(Plate plate){
        plates.add(plate);
    }

    @Override
    public void run() {
        try {
                for(Course course : Course.values()){
                    Food food = course.randomSelection();
                    waitor.newOrder(new Order(this, waitor, food));

                        Plate plate = plates.take();
                        System.out.println(this + " is eating " + plate);
                }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted!");
        }
        System.out.println(this + " eat over and leave!");
    }
}


class Waitor implements Runnable{
    private static int count = 0;
    final int id = count++;
    @Override
    public String toString(){
        return String.format("waitor%d", id);
    }
    private Restaurant restaurant;
    BlockingQueue<Plate> plates = new LinkedBlockingDeque<>();

    public Waitor(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
        while (!Thread.interrupted()) {

                Plate plate = plates.take();
                System.out.println(this + " get " + plate);
                plate.getOrder().getCustomer().deliver(plate);

        }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted!");
        }
        System.out.println(this + " off duty");
    }

    public void newOrder(Order order) {
        System.out.println(this + " got " + order);
        restaurant.orders.add(order);
    }
}

class Chef implements Runnable{
    private static int count = 0;
    private final int id = count++;
    @Override
    public String toString(){
        return "chef" + id;
    }
    private Restaurant restaurant;
    public Chef(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
        while (!Thread.interrupted()){

                Order order = restaurant.orders.take();
                System.out.println(this + " is dealing " + order);
//                Thread.sleep(restaurant.rand.nextInt(500));
            Thread.sleep(900);
                order.cooked();
        }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted!");
        }
        System.out.println(this + " off duty");
    }
}


