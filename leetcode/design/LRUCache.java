package design;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {

    Map<Integer, Integer> cache;
    int capacity;

    public LRUCache(int capacity) {
        cache = new LinkedHashMap<>(capacity);
        this.capacity = capacity;
    }

    public int get(int key) {
        makeNewest(key);
        return cache.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            makeNewest(key);
        } else if (capacity == 0) {
                int oldest = cache.keySet().iterator().next();
                cache.remove(oldest);
        } else --capacity;

        cache.put(key, value);
    }

    void makeNewest(int key) {
        if (cache.containsKey(key)) {
            int val = cache.get(key);
            cache.remove(key);
            cache.put(key, val);
        }
    }
}
