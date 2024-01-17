package ch13;

import java.util.ArrayList;

class Customer3 implements Runnable {
    private Table3 table;
    private String food;

    Customer3(Table3 table, String food) {
        this.table = table;
        this.food  = food;
    }

    public void run() {
        while(true) {
            try { Thread.sleep(100); } catch(InterruptedException e) {}
            String name = Thread.currentThread().getName();

            table.remove(food);
            System.out.println(name + " ate a " + food);
        }
    }
}

class Cook3 implements Runnable {
    private Table3 table;

    Cook3(Table3 table) { this.table = table; }

    public void run() {
        while(true) {
            int idx = (int) (Math.random() * table.dishNum());
            table.add(table.dishNames[idx]);

            try { Thread.sleep(10); } catch(InterruptedException e) {}
        }
    }
}

class Table3 {
    String[] dishNames = {"donut", "donut", "burger"}; // donut이 더 자주 나온다.
    final int MAX_FOOD = 6; // 테이블에 놓을 수 있는 최대 음식의 개수

    private ArrayList<String> dishes = new ArrayList<>();

    public synchronized void add(String dish) {
        while(dishes.size() >= MAX_FOOD) {
            String name = Thread.currentThread().getName();
            System.out.println(name+" is waiting.");
            try {
                wait(); // COOK쓰레드를 기다리게 한다.
                Thread.sleep(500);
            } catch(InterruptedException e) {}
        }
        dishes.add(dish);
        notify(); // 기다리고 있는 CUST를 깨우기 위함.
        System.out.println("Dishes:" + dishes.toString());
    }

    public void remove(String dishName) {
        synchronized (this) {
            String name = Thread.currentThread().getName();

            while (dishes.size() == 0) {
                System.out.println(name + " is waiting.");
                try {
                    wait(); // CUST쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

            while (true) {
                for (int i = 0; i < dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        notify(); // 잠자고 있는 COOK을 깨우기 위함
                        return;
                    }
                }

                try {
                    System.out.println(name + " is waiting.");
                    wait(); //  원하는 음식이 없는 CUST쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            } // end of while (true)
        } // end of synchronized
    }

    public int dishNum() { return dishNames.length; }
}

public class ThreadWaitEx3 {
    public static void main(String[] args) throws Exception {
        Table3 table = new Table3(); // 여러 쓰레드가 공유하는 객체

        new Thread(new Cook3(table), "COOK1").start();
        new Thread(new Customer3(table, "donut"),  "CUST1").start();
        new Thread(new Customer3(table, "burger"), "CUST2").start();

        // 0.1초(100 밀리 세컨드) 후에 강제 종료시킨다.
        Thread.sleep(2000);
        System.exit(0);
    }
}