import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] array;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        n = 0;
    }

    private class RandomIterator implements Iterator<Item> {
        private int i;
        private Item[] temp;

        public RandomIterator(Item[] array, int n) {
            temp = (Item[]) new Object[array.length];

            for (int a = 0; a < n; a++) {
                temp[a] = array[a];
            }
            i = n;
        }

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int rand = StdRandom.uniform(0, i);
            Item item = temp[rand];
            if (rand != i - 1) {
                temp[rand] = temp[i - 1];
            }
            i--;
            return item;
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        if (n == array.length) resize(array.length * 2);
        array[n++] = item;
    }

    private void resize(int size) {
        assert size >= n;
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("");
        int rand = StdRandom.uniform(0, n);
        Item item = array[rand];
        if (rand != n - 1) {
            array[rand] = array[n - 1];
        }
        array[n - 1] = null;
        n--;
        if (n == array.length / 4 && n > 0) resize(array.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("");
        int rand = StdRandom.uniform(0, n);
        Item item = array[rand];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator(array, n);
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        System.out.println("size: " + queue.size());

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

        Iterator<Integer> i = queue.iterator();
        Iterator<Integer> j = queue.iterator();

        while (i.hasNext()) {
            System.out.println(i.next());
        }

        while (j.hasNext()) {
            System.out.println(j.next());
        }
    }
}
