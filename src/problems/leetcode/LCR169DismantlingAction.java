package problems.leetcode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <a href = "https://leetcode.cn/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof/description/">LCR 169. 招式拆解 II</a>
 * <p>某套连招动作记作仅由小写字母组成的序列 arr，其中 arr[i] 第 i 个招式的名字。请返回第一个只出现一次的招式名称，如不存在请返回空格。
 * <p>使用数据结构 LinkedHashMap 节省第二次遍历
 * <p>时空复杂度：O(N)
 */
class LCR169DismantlingAction {

    public static void main(String[] args) {
        char res = new LCR169DismantlingAction().dismantlingAction("abbccdeff");
        System.out.println(res);
    }

    public char dismantlingAction(String arr) {
        Map<Character, Boolean> hmap = new LinkedHashMap<>();
        char[] sc = arr.toCharArray();
        for (char c : sc)
            hmap.put(c, !hmap.containsKey(c));
        Set<Map.Entry<Character, Boolean>> entries = hmap.entrySet();
        for (Map.Entry<Character, Boolean> d : entries) {
            if (d.getValue()) return d.getKey();
        }
        return ' ';
    }
}
