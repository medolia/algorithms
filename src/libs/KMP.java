package libs;

/**
 * KMP 字符匹配状态机
 * <p>
 * 状态定义：dp[stat][char] 代表 处于状态 stat 时遇到字符 char 进入的状态；
 * 关键是使用一个影子状态 X 记录之前状态从而实现状态回退；
 * 遍历解析：
 * 1. 将影子状态 X 复制到现在的状态数组，即若没有遇到正确字符需要回退到状态 X；
 * 2. 将正确字符对应的状态 + 1;
 * 3. 更新影子状态 X;
 */
public class KMP {
    private int[][] dp;
    private int N;

    public KMP(String pattern) {
        buildDFA(pattern);
    }

    public static void main(String[] args) {
        KMP machine = new KMP("ac");
        int startIdx = machine.search("hello");
        System.out.println("startIdx: " + startIdx);
    }

    private void buildDFA(String pattern) {
        N = pattern.length();
        dp = new int[N][256];

        dp[0][pattern.charAt(0)] = 1; // 初始化状态，只有遇到首个字符才能跳转到状态 1
        int X = 0; // 影子状态从 0 开始

        for (int j = 1; j < N; j++) { // 从状态 1 开始
            for (int ch = 0; ch <= 255; ch++)
                dp[j][ch] = dp[X][ch]; // 1
            // 例：假设 pattern 为 "al"，开始状态为 0，处于状态 0 时遇到字符 "a" 进入状态 1；
            //    处于状态 1 时遇到字符 "l" 进入状态 2，即已经找到了完整模式串；
            dp[j][pattern.charAt(j)] = j + 1; //2
            X = dp[X][pattern.charAt(j)]; // 3
        }
    }

    /**
     * 搜寻主字符串 mStr 中包含完整 pattern 的起始索引，若找不到返回 -1；
     */
    public int search(String mStr) {
        int stat = 0;
        for (int i = 0; i < mStr.length(); i++) {
            stat = dp[stat][mStr.charAt(i)];
            if (stat == N) return i - N + 1;
        }

        return -1;
    }
}
