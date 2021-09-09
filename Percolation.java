import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] id;
    private int openSite = 0;
    private final int num;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("value can't be 0");
        id = new boolean[n * n + 2];
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 2);
        num = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndex(row, col);
        if (row < 1 || row > num || col < 1 || col > num)
            throw new IllegalArgumentException("r and c should between 1 and n");
        if (!isOpen(row, col)) {
            id[index] = true;
            openSite += 1;

            if (row == 1) {
                uf.union(index, 0);
                uf2.union(index, 0);
            }
            if (row == num) uf2.union(index, num * num + 1);
            // check up
            if (row != 1)
                if (isOpen(row - 1, col)) {
                    uf.union(index, (num * (row - 2) + col));
                    uf2.union(index, (num * (row - 2) + col));
                }
            // check down
            if (row != num)
                if (isOpen(row + 1, col)) {
                    uf.union((num * row + col), index);
                    uf2.union((num * row + col), index);
                }
            // check left
            if (col != 1)
                if (isOpen(row, col - 1)) {
                    uf.union(index - 1, index);
                    uf2.union(index - 1, index);
                }
            // check right
            if (col != num)
                if (isOpen(row, col + 1)) {
                    uf.union(index + 1, index);
                    uf2.union(index + 1, index);
                }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        if (row < 1 || row > num || col < 1 || col > num)
            throw new IllegalArgumentException("r and c should between 1 and n");
        if (id[index]) return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        if (row < 1 || row > num || col < 1 || col > num)
            throw new IllegalArgumentException("r and c should between 1 and n");
        if (isOpen(row, col) && (uf.find(0) == uf.find(index))) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf2.find(0) == uf2.find((num * num + 1));
    }

    private int getIndex(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) return -1;
        else return ((row - 1) * num + col);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);

        while (!StdIn.isEmpty()) {
            int r = StdIn.readInt();
            int c = StdIn.readInt();
            perc.open(r, c);
        }
    }
}
