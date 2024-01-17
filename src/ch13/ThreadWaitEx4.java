package ch13;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Customer4 implements Runnable {
    private Table4 table;
    private String food;

    Customer4(Table4 table, String food) {
        this.table = table;
        this.food  = food;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {}

            String name = Thread.currentThread().getName();

            table.remove(food);
            System.out.println(name + " ate a " + food);
        } // end of while
    }
}

class Cook4 implements Runnable {
    private Table4 table;

    Cook4(Table4 table) {
        this.table = table;
    }

    public void run() {
        while(true) {
            int idx = (int)(Math.random()*table.dishNum());
            table.add(table.dishNames[idx]);
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {}
        }
    }
}

class Table4 {
    String[] dishNames = { "donut","donut","burger" };
    final int MAX_FOOD = 6;
    private ArrayList<String> dishes = new ArrayList<>();

    private ReentrantLock lock = new ReentrantLock();
    private Condition forCook = lock.newCondition();
    private Condition forCust = lock.newCondition();

    public void add(String dish) {
        lock.lock();

        try {
            while(dishes.size() >= MAX_FOOD) {
                String name = Thread.currentThread().getName();
                System.out.println(name+" is waiting1.");
                try {
                    forCook.await();
                    Thread.sleep(500);
                } catch(InterruptedException e) {}
            }

            dishes.add(dish);
            forCust.signal();
            System.out.println("Dishes:" + dishes.toString());
        } finally {
            lock.unlock();
        }
    }

    public void remove(String dishName) {
        lock.lock();
        String name = Thread.currentThread().getName();

        try {
            while(dishes.size()==0) {
                System.out.println(name+" is waiting2-1.");
                try {
                    forCust.await();
                    Thread.sleep(500);
                } catch(InterruptedException e) {}
            }

            while(true) {
                for(int i=0; i<dishes.size(); i++) {
                    if(dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        forCook.signal();
                        return;
                    }
                }

                try {
                    System.out.println(name+" is waiting2-2.");
                    forCust.await();
                    Thread.sleep(500);q
                } catch(InterruptedException e) {}
            }
        } finally {
            lock.unlock();
        }
    }

    public int dishNum() {
        return dishNames.length;
    }
}

public class ThreadWaitEx4 {
    public static void main(String[] args) throws Exception {
        Table4 table = new Table4();

        new Thread(new Cook4(table), "COOK1").start();
        new Thread(new Customer4(table, "donut"),  "CUST1").start();
        new Thread(new Customer4(table, "burger"), "CUST2").start();

        Thread.sleep(2000);
        System.exit(0);
    }
}
