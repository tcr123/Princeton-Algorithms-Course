import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            queue.enqueue(word);
        }

        for (int i = 0; i < n; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
