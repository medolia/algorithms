package problems.other.compositeIterator;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 341. 扁平化嵌套列表迭代器
 *
 * 给你一个嵌套的整型列表。请你设计一个迭代器，使其能够遍历这个整型列表中的所有整数。
 * 列表中的每一项或者为一个整数，或者是另一个列表。其中列表的元素也可能是整数或是其他列表。
 *
 * 思路：使用一个迭代器栈辅助，类似设计模式 - 组合器模式关于迭代的处理；
 *
 * 组合模式中存在两种对象，一种单一型，一种复合型，一种复合型可以包含多个其他复合型或者单一型（通常用一个泛型列表变量维护）
 * 而单一型里并不包含任何其他单一或者复合型；
 *
 * 栈中的每个迭代器都记有当前迭代器指针的位置，所以每遇到一个复合型的对象直接将其迭代器压入栈顶即可；
 * hasNext() 实现：栈为空那一定已经迭代完所有元素了，返回 false；若非空，则看栈顶的迭代器是否已经
 * 迭代完所有元素，若是则弹出，然后递归 hasNext()，若否则返回 true；
 * next() 实现：需要 hasNext() 判定为 true，然后取出栈顶的迭代器下一个元素，如果它是
 * 复合型则将其迭代器压入栈顶，然后递归 next()，而如果是单一型的则返回值；
 */
public class NestedIterator implements Iterator<Integer> {

    Stack<Iterator<NestedInteger>> stack = new Stack<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        stack.push(nestedList.iterator());
    }

    @Override
    public Integer next() {
        if (hasNext()) {
            Iterator<NestedInteger> iterator = stack.peek();
            NestedInteger nestedInteger = iterator.next();
            if (!nestedInteger.isInteger()) {
                stack.push(nestedInteger.getList().iterator());
                return next();
            }
            return nestedInteger.getInteger();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty())
            return false;
        else {
            Iterator<NestedInteger> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }
}
