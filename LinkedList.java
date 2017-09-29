import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Programming Quiz 3 - Fun with recursion
 *
 * Replace <T2ID> with your T-Square ID in the author tag below,
 * then fill-in the implementations of the three recursive helper methods
 * recursiveSize, recursiveToString, and recursiveReverse. Each helper method is
 * worth 35 points and must be recursive.
 * The bonus methods are worth 5 points each.
 * When you're finished, attach this file to your T-Square submission for PQ3.
 *
 * @author <T2ID>
 */
public class LinkedList<E> implements Iterable<E> {

    private class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }
    }

    private Node<E> head = null;

    public E pop() {
        E answer = head.element;
        head = head.next;
        return answer;
    }

    public E peek() {
        return head.element;
    }

    // Notice that new elements are added to the front of the list, not the back
    public LinkedList<E> add(E e) {
        head = new Node<E>(e, head);
        return this;
    }

    /**
     * Returns the number of elements in this LinkedList.
     */
    public int size() {
        return recursiveSize(head, 0);
    }

    private int recursiveSize(Node node, int accum) {
        // Your implementation goes here.
        // Replace this stubbed return statement:
        if (node == null) {
            return accum;
        } else {
            accum++;
            return recursiveSize(node.next, accum);
        }
    }

    /**
     * Returns a String representation of the list such as:
     *
     * [element1,element2,element3,]
     *
     * Notice the comma after the last element.
     */
    public String toString() {
        return "[" + recursiveToString(head, "") + "]";
    }

    private String recursiveToString(Node<E> node, String accum) {
        // Your implementation goes here.
        // Replace this stubbed return statement:
        if (node == null) {
            return accum;
        } else {
            accum += node.element.toString() + ",";
            return recursiveToString(node.next, accum);
        }
    }

    /**
     * Returns a new LinkedList with the same elements as this LinkedList
     * but whose elements are in reverse order from this LinkedList.
     */
    public LinkedList<E> reverse() {
        return recursiveReverse(head, new LinkedList<E>());
    }

    public LinkedList<E> recursiveReverse(Node<E> node, LinkedList<E> accum) {
        // Your implementation goes here.
        // Replace this stubbed return statement:
        if (node == null) {
            return accum;
        } else {
            accum.add(node.element);
            return recursiveReverse(node.next, accum);
        }
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> cursor = head;
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E answer = cursor.element;
                cursor = cursor.next;
                return answer;
            }

            public boolean hasNext() {
                return cursor != null;
            }
        };
    }

    // Bonus:
    // The three recursive helper methods you defined above exhibit a pattern.
    // Use the fold method from Exam 3 (defined below) to write folding versions
    // of size, toString, and reverse using fold instead of specialized helper
    // methods.

    private <E, R> R fold(R init, LinkedList<E> es, BiFunction<R, E, R> f) {
        R accum = init;
        for (E e: es) accum = f.apply(accum, e);
        return accum;
    }

    public int sizeByFold() {
        // Your implementation goes here.
        // Replace this stubbed return statement:
        return fold(0, this, (a, b) -> {
                a++;
                return a;
            });
    }

    public String toStringByFold() {
        // Your implementation goes here.
        // Replace this stubbed return statement:
        return "[" + fold("", this, (a, b) -> {
                a += b.toString() + ",";
                return a;
            }) + "]";
    }

    public LinkedList<E> reverseByFold() {
        // Your implementation goes here.
        // Replace this stubbed return statement:
        return fold(new LinkedList<E>(), this, (a, b) -> {
                a.add(b);
                return a;
            });
    }

    /**
     * Running this class should produce the following output on the console:
     *
     * [4,3,2,1,]
     * 4
     * [1,2,3,4,]
     * [4,3,2,1,]
     * 4
     * [1,2,3,4,]
     */
    public static void main(String[] args) {
        LinkedList<Integer> ints = new LinkedList<>();
        IntStream.range(1, 5).forEach(e -> ints.add(e));
        System.out.println(ints);
        System.out.println(ints.size());
        System.out.println(ints.reverse());
        System.out.println(ints.toStringByFold());
        System.out.println(ints.sizeByFold());
        System.out.println(ints.reverseByFold());
    }
}
