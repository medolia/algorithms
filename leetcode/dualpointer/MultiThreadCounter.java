package dualpointer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class MultiThreadCounter {

    public static void main(String[] args) throws Exception {

        // 数据准备
        int size = 1000036;
        String[] arr = new String[size];
        for (int i = 36; i < size; i++) {
            arr[i] = String.valueOf(i & 7);
        }
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i; j < 8; j++) {
                arr[index++] = String.valueOf(i);
            }
        }

        // 运行10次，对比结果和时间
        for (int j = 0; j < 10; j++) {
            long start = System.currentTimeMillis();

            // 打印结果
            System.out.println(count(arr));

            // 执行用时
            System.out.println("执行用时：" + (System.currentTimeMillis() - start));
        }
    }

    private static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final int LENGTH_THRESHOLD = 1000;
    private static final Object lock = new Object();
    private static ConcurrentHashMap<String, Integer> GLOBAL_COUNTER;

    private static Map<String, Integer> count(String[] arr) throws Exception {
        synchronized (lock) {
            GLOBAL_COUNTER = new ConcurrentHashMap<>();
            count(arr, 0, arr.length - 1);

            while (!((ThreadPoolExecutor) EXECUTOR_SERVICE).getQueue().isEmpty()) {
                //
            }

            return new HashMap<>(GLOBAL_COUNTER);
        }
    }

    private static void count(String[] arr, int l, int r) throws Exception {
        if (l > r) {
            return;
        }

        if (r - l <= LENGTH_THRESHOLD) {
            EXECUTOR_SERVICE.submit(() -> {
                System.out.printf("l:%d, r:%d%n", l, r);
                for (int i = l; i <= r; i++) {
                    GLOBAL_COUNTER.merge(arr[i], 1, Integer::sum);
                }
            });
            return;
        }

        int mid = l + (r - l) / 2;
        count(arr, l, mid - 1);
        count(arr, mid, r);
    }

}
