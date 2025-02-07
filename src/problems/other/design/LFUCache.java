package problems.other.design;

import java.util.*;

/**
 * 460. LFU 缓存
 *
 * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
 * 实现 LFUCache 类：
 *
 * LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
 * int get(int key) - 如果键存在于缓存中，则获取键的值，否则返回 -1。
 * void put(int key, int value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之
 * 前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最久未使用 的键。
 * 注意「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。
 *
 * 进阶：你是否可以在 O(1) 时间复杂度内执行两项操作？
 *
 * 思路：使用三个散列表：键-值 键-频率 频率-键（链接散列集），每次操作更新键相关的三个表；
 */
public class LFUCache {
    // key 到 val 的映射，我们后文称为 KV 表
    private HashMap<Integer, Integer> keyToVal;
    // key 到 freq 的映射，我们后文称为 KF 表
    private HashMap<Integer, Integer> keyToFreq;
    // freq 到 key 列表的映射，我们后文称为 FK 表
    private HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;
    // 记录最小的频次
    private int minFreq;
    // 记录 LFU 缓存的最大容量
    private int cap;

    public LFUCache(int capacity) {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        this.cap = capacity;
        this.minFreq = 0;
    }

    public int get(int key) {
        if (keyToVal.containsKey(key)) {
            increaseFreq(key);
            return keyToVal.get(key);
        } else return -1;
    }

    public void put(int key, int value) {
        if (cap <= 0) return;

        if (keyToVal.containsKey(key)) {
            increaseFreq(key);
            keyToVal.put(key, value);
        } else {
            if (keyToVal.size() >= cap) delMinFreqKey();
            keyToVal.put(key, value);
            keyToFreq.put(key, 1);
            freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
            freqToKeys.get(1).add(key);
            this.minFreq = 1;
        }
    }

    private void increaseFreq(int key) {
        int freq = keyToFreq.get(key);
        keyToFreq.put(key, freq+1);

        freqToKeys.get(freq).remove(key);

        freqToKeys.putIfAbsent(freq+1, new LinkedHashSet<>());
        freqToKeys.get(freq+1).add(key);

        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            if (freq == minFreq)
                ++minFreq;
        }
    }

    private void delMinFreqKey() {
        LinkedHashSet<Integer> keyList = freqToKeys.get(minFreq);
        int tgtKey = keyList.iterator().next();

        keyList.remove(tgtKey);
        if (keyList.isEmpty()) freqToKeys.remove(minFreq);
        keyToVal.remove(tgtKey);
        keyToFreq.remove(tgtKey);
    }
}
