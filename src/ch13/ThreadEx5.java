package ch13;

public class ThreadEx5 {
    static long startTime = 0;

    public static void main(String[] args) {
        Runnable rn1 = new ThreadEx5_1();
        Thread th1 = new Thread(rn1);

        startTime = System.currentTimeMillis();
        th1.start();
//        th1.run();

        for (int i=0; i<500; i++)
            System.out.printf("%s", new String("-"));

        System.out.print("소요시간1:" + (System.currentTimeMillis() - ThreadEx5.startTime));
    }
}

class ThreadEx5_1 implements Runnable {
    public void run() {
        for (int i=0; i<500; i++)
            System.out.printf("%s", new String("|"));

        System.out.print("소요시간2:" + (System.currentTimeMillis() - ThreadEx5.startTime));
    }
}
