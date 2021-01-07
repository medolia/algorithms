package design;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<List<Integer>> res = NSum.recurForResult(new int[] {1,1,2,3,5,4,-1}, 3, 5);
    }
}
