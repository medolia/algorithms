package templates;

import java.util.Arrays;
import java.util.Random;

/**
 * 蓄水池算法 适用于从一个数据流中平等地抽取 k 个样本（数据流）
 *
 * @author lilongbin
 */
class ReservoirSample {

    public static void main(String[] args) {
        int[] stream = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        int k = 3;

        int[] res = ReservoirSample.reservoir(stream, k);
        System.out.println(Arrays.toString(res));
    }

    private static final Random random = new Random();

    public static int[] reservoir(int[] stream, int k) {
        int[] res = new int[k];

        System.arraycopy(stream, 0, res, 0, k);

        for (int i = k; i < stream.length; i++) {
            int ri = random.nextInt(i + 1);
            if (ri < k) {
                res[ri] = stream[i];
            }
        }

        return res;
    }

}
