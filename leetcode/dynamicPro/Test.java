package dynamicPro;

public class Test {
    public static void main(String[] args) {
        KMP machine = new KMP("ac");
        int startIdx = machine.search("hello");
        System.out.println("startIdx: " + startIdx);
    }
}
