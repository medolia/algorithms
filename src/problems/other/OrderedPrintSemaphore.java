package problems.other;

import java.util.concurrent.Semaphore;

class OrderedPrintSemaphore {


    // 全局变量 count
    private int count = 1;

    Semaphore odd = new Semaphore(1);
    Semaphore even = new Semaphore(0);

    public static void main(String[] args) {
        OrderedPrintSemaphore orderedPrintSemaphore = new OrderedPrintSemaphore();
        orderedPrintSemaphore.turning();

    }

    public void turning() {
        Thread evenThread = new Thread(() -> {
            while (count < 100) {
                // 获取锁

                while (!even.tryAcquire()) {
                    //
                }

                if ((count & 1) == 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    odd.release();
                }

            }
        }, "偶数");
        Thread oddThread = new Thread(() -> {
            while (count < 100) {

                while (!odd.tryAcquire()) {
                    //
                }

                // 只处理奇数
                if ((count & 1) == 1) {
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    even.release();
                }

            }
        }, "奇数");
        evenThread.start();
        oddThread.start();
    }




}
