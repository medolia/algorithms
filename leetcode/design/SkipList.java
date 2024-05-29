package design;


import java.util.Random;

/**
 * 跳表实现
 * <a href='https://www.javatpoint.com/skip-list-in-data-structure#:~:text=The%20skip%20list%20is%20used,known%20as%20a%20skip%20list.'>doc</a>
 *
 * @author lbli
 */
public class SkipList {
    private static final int MAX_LEVEL = 16;
    private SkipListNode head;
    private int level;
    private Random random;

    public SkipList() {
        this.level = 0;
        this.head = new SkipListNode(MAX_LEVEL, Integer.MIN_VALUE);
        this.random = new Random();
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();

        skipList.insert(3);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(9);
        skipList.insert(12);
        skipList.insert(19);
        skipList.insert(17);
        skipList.insert(26);
        skipList.insert(21);
        skipList.insert(25);

        System.out.println(skipList.search(19)); // true
        System.out.println(skipList.search(15)); // false

        skipList.delete(19);
        System.out.println(skipList.search(19)); // false
    }

    private int randomLevel() {
        int lvl = 0;
        while (lvl < MAX_LEVEL && random.nextBoolean()) {
            lvl++;
        }
        return lvl;
    }

    public void insert(int value) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                update[i] = head;
            }
            level = newLevel;
        }

        SkipListNode newNode = new SkipListNode(newLevel, value);
        for (int i = 0; i <= newLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }
    }

    public boolean search(int value) {
        SkipListNode current = head;

        // 每次插入 level 随机且最高只能是maxLevel+1，所以高 level 元素更少，从高往低更效率
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
        }

        current = current.forward[0];
        return current != null && current.value == value;
    }

    public void delete(int value) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];
        if (current != null && current.value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != current) break;
                update[i].forward[i] = current.forward[i];
            }

            while (level > 0 && head.forward[level] == null) {
                level--;
            }
        }
    }

    static class SkipListNode {
        int value;
        SkipListNode[] forward;

        public SkipListNode(int level, int value) {
            this.value = value;
            this.forward = new SkipListNode[level + 1];
        }
    }
}

