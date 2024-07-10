package design;


import java.util.Random;

/**
 * 跳表实现
 * <a href='https://www.javatpoint.com/skip-list-in-data-structure#:~:text=The%20skip%20list%20is%20used,known%20as%20a%20skip%20list.'>doc</a>
 *
 * @author lbli
 */
class SkipList {

    int level, maxLevel;
    Random random;
    Node head;

    public SkipList() {
        level = 0;
        maxLevel = 16;
        random = new Random();
        head = new Node(Integer.MIN_VALUE, maxLevel);
    }

    public void insert(int value) {
        Node[] update = new Node[maxLevel + 1];
        Node current = this.head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current == null || current.value != value) {
            int lvl = randomLevel();

            if (lvl > level) {
                for (int i = level + 1; i <= lvl; i++) {
                    update[i] = head;
                }
                level = lvl;
            }

            Node newNode = new Node(value, lvl);
            for (int i = 0; i <= lvl; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
    }

    private int randomLevel() {
        int lvl = 0;
        while (lvl < maxLevel && random.nextDouble() < 0.5) {
            lvl++;
        }
        return lvl;
    }

    public boolean search(int value) {
        Node current = this.head;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
        }
        current = current.forward[0];
        return current != null && current.value == value;
    }

    public void delete(int value) {
        Node[] update = new Node[maxLevel + 1];
        Node current = this.head;

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

    static class Node {
        int value;
        Node[] forward;

        Node(int value, int level) {
            this.value = value;
            this.forward = new Node[level + 1];
        }
    }

}

