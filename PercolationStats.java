import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // a represent percolation threshold
    private final double[] a;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trials should more than 0");
        a = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int r = StdRandom.uniform(1, n + 1);
                int c = StdRandom.uniform(1, n + 1);
                while (perc.isOpen(r, c)) {
                    r = StdRandom.uniform(1, n + 1);
                    c = StdRandom.uniform(1, n + 1);
                }
                perc.open(r, c);
            }
            a[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(a);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(a);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(a.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(a.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percstat = new PercolationStats(n, t);
        Stopwatch stopwatch = new Stopwatch();
        System.out.printf("mean                     = %f\n", percstat.mean());
        System.out.printf("stddev                   = %.15f\n", percstat.stddev());
        System.out.printf("95%% confidence interval = [%.15f, %.15f]\n",
                          percstat.confidenceLo(), percstat.confidenceHi());
        double time = stopwatch.elapsedTime();
        System.out.printf("Running time : %.6f\n", time);
    }
}
