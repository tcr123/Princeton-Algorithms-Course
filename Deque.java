import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;
    private Node head, tail;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        Node temp = new Node();
        temp.item = item;

        if (head == null) {
            head = temp;
            tail = temp;
            n++;
        }
        else {
            head.prev = temp;
            temp.next = head;
            temp.prev = null;
            head = temp;
            n++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        Node temp = new Node();
        temp.item = item;

        if (tail == null) {
            head = temp;
            tail = temp;
            n++;
        }
        else {
            tail.next = temp;
            temp.next = null;
            temp.prev = tail;
            tail = temp;
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("");

        Item item = head.item;
        if (n == 1) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (tail == null) throw new NoSuchElementException("");

        Item item = tail.item;
        if (n == 1) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Stack Overflow");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("");
            else {
                Node node = current;
                current = current.next;
                return node.item;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        System.out.println("size: " + deque.size());

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);

        System.out.println("Remove Last: " + deque.removeLast());
        System.out.println("Remove First: " + deque.removeFirst());
        System.out.println("Remove First: " + deque.removeFirst());
        System.out.println("size: " + deque.size());

        System.out.println("Remove Last: " + deque.removeLast());
        System.out.println("Remove Last: " + deque.removeLast());
        System.out.println("size: " + deque.size());

        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        System.out.println("size: " + deque.size());

        Iterator<Integer> i = deque.iterator();
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.next());
        System.out.println(i.next());
    }
}


