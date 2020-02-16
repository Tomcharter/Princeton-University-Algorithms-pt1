import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private static double[] results;
	private double stddevValue = -1;
	private double meanValue = -1;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int attempts) {

		if (n <= 0 || attempts <= 0) {
			throw new IllegalArgumentException();
		}

		results = new double[attempts];

		// Monte Carlo simulation to find the point the grid percolates by starting with
		// no cells open and opening a cell at random until the grid percolates
		for (int i = 0; i < attempts; i++) {

			Percolation percolation = new Percolation(n);

			while (!percolation.percolates()) {
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				if (!percolation.isOpen(row, col)) {
					percolation.open(row, col);
				}
			}
			
			results[i] = (double) percolation.numberOfOpenSites() / (n * n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		meanValue = StdStats.mean(results);
		return meanValue;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		stddevValue = StdStats.stddev(results);
		return stddevValue;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		if (meanValue == -1)
			mean();
		if (stddevValue == -1)
			stddev();
		return meanValue - (1.96 * stddevValue / Math.sqrt(results.length));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		if (meanValue == -1)
			mean();
		if (stddevValue == -1)
			stddev();
		return meanValue + (1.96 * stddevValue / Math.sqrt(results.length));
	}

	// test client
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int attempts = Integer.parseInt(args[1]);

		PercolationStats percolationStats = new PercolationStats(n, attempts);

		System.out.println("mean = " + percolationStats.mean());
		System.out.println("stddev = " + percolationStats.stddev());
		System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", "
				+ percolationStats.confidenceHi() + "]");
	}
}
