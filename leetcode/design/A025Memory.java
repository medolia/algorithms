package design;

import java.util.*;

/**
 * 这是一个内存分配的编码题。内存是连续的，每次分配会获取其中连续的一段。
 * 例如长度为1000的内存，分配100，200，400，则分别分配到
 * 0~100, 100~300, 300~700这三段空间。分配内存时参数是长度，返回起始位置0， 100， 300即可。
 * <p>
 * 释放内存则是将指定位置的内存释放掉，入参是内存起始位置，返回被释放内存的长度
 * <p>
 * 碎片整理可以单独调用，申请和释放时无需考虑。内存在分配释放之后，会产生碎片
 * 例如释放了100~300，则由于300~700被占用，剩余内存为分开的两段，100~300，700~1000。
 * 无法直接申请500长度的连续内存，则需要将0~100和300~700整理到一起（整理为0~100，100~500）
 * 整理之后，已被申请的内存位置发生变化，这里无需考虑整理前后的映射关系，使用新的内存位置进行释放即可
 */
@SuppressWarnings("ALL")
class A025Memory {

    public static void main(String[] args) {
        A025Memory a025Memory = new A025Memory(1000);

        int idx1 = a025Memory.allocate(100);
        System.out.printf("1 %s\n", idx1);
        int idx2 = a025Memory.allocate(200);
        System.out.printf("2 %s\n", idx2);
        int idx3 = a025Memory.allocate(400);
        System.out.printf("3 %s\n", idx3);
        a025Memory.release(idx2);
        int idx4 = a025Memory.allocate(300);
        System.out.printf("4 %s\n", idx4);
        int idx5 = a025Memory.allocate(200);
        System.out.printf("5 %s\n", idx5);
        int idx6 = a025Memory.allocate(100);
        System.out.printf("6 %s\n", idx6);
        a025Memory.release(idx4);
        a025Memory.release(idx5);
        int idx7 = a025Memory.allocate(300);
        System.out.printf("7 %s\n", idx7);
        int idx8 = a025Memory.allocate(200);
        System.out.printf("8 %s\n", idx8);
        a025Memory.release(idx7);
        a025Memory.release(idx8);
        int idx9 = a025Memory.allocate(500);
        System.out.printf("9 %s\n", idx9);
        a025Memory.defragment();
        int idx10 = a025Memory.allocate(500);
        System.out.printf("10 %s\n", idx10);
        int idx11 = a025Memory.allocate(400);
        System.out.printf("10 %s\n", idx11);
    }

    private final int size;
    List<Block> allocatedBlockList;

    public A025Memory(int size) {
        this.size = size;
        allocatedBlockList = new ArrayList<>();
    }

    /**
     * 如果能分配到内存，则返回内存位置，如果分配不到，则返回-1
     *
     * @param len 申请的内存长度
     * @return 内存索引位置
     */
    int allocate(int len) {
        if (len <= 0 || len > this.size) {
            throw new IllegalArgumentException("valid size");
        }

        if (allocatedBlockList.isEmpty()) {
            allocatedBlockList.add(new Block(0, len));
            return 0;
        }

        for (int i = 0; i < allocatedBlockList.size(); i++) {
            Block curr = allocatedBlockList.get(i);

            // 第一个左边
            if (i == 0 && curr.l >= len) {
                allocatedBlockList.add(0, new Block(0, len));
            }


            // 两两之间的间隙
            if (i > 0) {
                Block prev = allocatedBlockList.get(i - 1);
                int prevR = prev.r();
                if (curr.l - prevR >= len) {
                    allocatedBlockList.add(i, new Block(prevR, len));
                    return prev.l;
                }
            }

            // 最后一个右边
            if (i == allocatedBlockList.size() - 1 && (size - curr.r() >= len)) {
                allocatedBlockList.add(new Block(curr.r(), len));
                return curr.r();
            }
        }

        return -1;
    }

    /**
     * 释放内存，如果内存本身不存在则不处理
     *
     * @param index 释放内存的索引
     * @return 被释放内存的长度
     */
    int release(int index) {
        Iterator<Block> iterator = allocatedBlockList.iterator();
        while (iterator.hasNext()) {
            Block curr = iterator.next();
            if (curr.l == index) {
                iterator.remove();
                return curr.len;
            }
        }

        return 0;
    }

    /**
     * 碎片整理
     */
    void defragment() {
        allocatedBlockList.sort(Comparator.comparingInt(e -> e.l));

        int currStart = 0;
        for (Block block : allocatedBlockList) {
            block.l = currStart;
            currStart += block.len;
        }
    }

    static class Block {
        int l, len;

        public Block(int l, int len) {
            this.l = l;
            this.len = len;
        }

        int r() {
            return l + len;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Block block = (Block) o;
            return l == block.l && len == block.len;
        }

        @Override
        public int hashCode() {
            return Objects.hash(l, len);
        }

        @Override
        public String toString() {
            return Arrays.toString(new int[]{l, l + len});
        }
    }
}
