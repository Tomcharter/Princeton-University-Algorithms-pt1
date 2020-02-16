import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF uf;
	private boolean[] isOpenArr;
	private int size;
	private int nOpenSites;
	private int index;
	private int arrSize;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		arrSize = n * n;
		size = n;
		nOpenSites = 0;
		isOpenArr = new boolean[n * n + 1];
		uf = new WeightedQuickUnionUF(n * n + 2);
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {

		if (!isOpen(row, col)) {

			nOpenSites++;
			index = getArrIndex(row, col);
			isOpenArr[index] = true;

			// If the site is on the first row then union it with index 0
			if (row == 1) {
				uf.union(index, 0);
			}

			// If the site is on the last row then union it with index n + 1
			if (row == size) {
				uf.union(index, arrSize + 1);
			}

			// Check cell to the left and union if open
			if (row > 1 && isOpen(row - 1, col)) {
				uf.union(index, getArrIndex(row - 1, col));
			}

			// Check cell to the right and union if open
			if (row < size && isOpen(row + 1, col)) {
				uf.union(index, getArrIndex(row + 1, col));
			}

			// Check cell above and union if open
			if (col < size && isOpen(row, col + 1)) {
				uf.union(index, getArrIndex(row, col + 1));
			}

			// Check cell below and union if open
			if (col > 1 && isOpen(row, col - 1)) {
				uf.union(index, getArrIndex(row, col - 1));
			}
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		return isOpenArr[getArrIndex(row, col)];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		return uf.find(getArrIndex(row, col)) == uf.find(0);
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return nOpenSites;
	}

	// does the system percolate?
	// if index arrSize + 1 and index 0 are part of the same union then the system
	// percolates
	public boolean percolates() {
		return uf.find(arrSize + 1) == uf.find(0);
	}

	private int getArrIndex(int row, int col) {
		return (row - 1) * size + col;
	}
}
