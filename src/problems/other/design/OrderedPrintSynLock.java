package problems.other.design;

class OrderedPrintSynLock {


    // 全局变量 count
    private int count = 0;

    // 锁
    private final Object lock = new Object();

    public static void main(String[] args) {
        OrderedPrintSynLock test = new OrderedPrintSynLock();
        test.turning();
    }

    public void turning() {
        Thread even = new Thread(() -> {
            while (count < 100) {
                // 获取锁
                synchronized (lock) {
                    // 只处理偶数
                    if ((count & 1) == 0) {
                        System.out.println(Thread.currentThread().getName() + ": " + count++);
                    }
                }
            }
        }, "偶数");
        Thread odd = new Thread(() -> {
            while (count < 100) {
                // 获取锁
                synchronized (lock) {
                    // 只处理奇数
                    if ((count & 1) == 1) {
                        System.out.println(Thread.currentThread().getName() + ": " + count++);
                    }
                }
            }
        }, "奇数");
        even.start();
        odd.start();
    }




}
