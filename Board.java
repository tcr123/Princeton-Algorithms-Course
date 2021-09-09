import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

public class Board {
    private final int offset = 1;
    private int board[];
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[getIndex1D(i, j)] = tiles[i][j];
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                s.append(board[getIndex1D(i, j)] + " ");
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int tiles = 0;
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i + offset) tiles++;
        }
        return tiles;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        int x1, x2, y1, y2;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != i + offset && board[i] != 0) {
                x1 = getRowCol(i)[0];
                y1 = getRowCol(i)[1];

                x2 = getRowCol(board[i] - offset)[0];
                y2 = getRowCol(board[i] - offset)[1];

                sum = sum + (Math.abs(x1 - x2) + Math.abs(y1 - y2));
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i + offset) return false;
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < board.length; i++) {
            if (this.board[i] != that.board[i])
                return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbours = new Queue<Board>();
        ArrayList<Integer> exchBoard = new ArrayList<Integer>();
        int row, col;
        int up, down, left, right;
        int emptyIndex;

        for (emptyIndex = 0; emptyIndex < board.length; emptyIndex++) {
            if (board[emptyIndex] == 0) {
                row = getRowCol(emptyIndex)[0];
                col = getRowCol(emptyIndex)[1];
                // check up
                if (row - 1 >= 0) {
                    up = getIndex1D(row - 1, col);
                    exchBoard.add(up);
                }
                // check down
                if (row + 1 < n) {
                    down = getIndex1D(row + 1, col);
                    exchBoard.add(down);
                }
                // check left
                if (col - 1 >= 0) {
                    left = getIndex1D(row, col - 1);
                    exchBoard.add(left);
                }
                // check right
                if (col + 1 < n) {
                    right = getIndex1D(row, col + 1);
                    exchBoard.add(right);
                }
                break;
            }
        }

        for (int i = 0; i < exchBoard.size(); i++) {
            int[] tempBoard1D;
            int[][] tempBoard2D;
            tempBoard1D = copy1DArray(board);
            exchange(tempBoard1D, emptyIndex, exchBoard.get(i));
            tempBoard2D = copy2DArray(tempBoard1D, this.n);
            Board newBoard = new Board(tempBoard2D);
            neighbours.enqueue(newBoard);
        }

        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int row, col;
        int up, down, left, right;

        int[] twin1D = new int[board.length];
        int[][] twin2D;

        for (int i = 0; i < board.length; i++) {
            twin1D[i] = board[i];
        }

        for (int i = 0; i < twin1D.length; i++) {
            if (twin1D[i] != 0) {
                row = getRowCol(i)[0];
                col = getRowCol(i)[1];
                // check up
                if (row - 1 >= 0) {
                    up = getIndex1D(row - 1, col);
                    if (twin1D[up] != 0) {
                        exchange(twin1D, i, up);
                        break;
                    }
                }
                // check down
                if (row + 1 < n) {
                    down = getIndex1D(row + 1, col);
                    if (twin1D[down] != 0) {
                        exchange(twin1D, i, down);
                        break;
                    }
                }
                // check left
                if (col - 1 >= 0) {
                    left = getIndex1D(row, col - 1);
                    if (twin1D[left] != 0) {
                        exchange(twin1D, i, left);
                        break;
                    }
                }
                // check right
                if (col + 1 < n) {
                    right = getIndex1D(row, col + 1);
                    if (twin1D[right] != 0) {
                        exchange(twin1D, i, right);
                        break;
                    }
                }
            }
        }

        twin2D = copy2DArray(twin1D, this.n);
        return new Board(twin2D);
    }

    private void exchange(int[] a, int empty, int locChange) {
        int temp = a[empty];
        a[empty] = a[locChange];
        a[locChange] = temp;
    }

    private int getIndex1D(int row, int col) {
        return (row * n + col % n);
    }

    private int[] getRowCol(int i) {
        int[] rowCol = new int[2];
        rowCol[0] = i / n; // row
        rowCol[1] = i % n; // col
        return rowCol;
    }

    private int[] copy1DArray(int[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        return b;
    }

    private int[][] copy2DArray(int[] a, int width) {
        int[][] b = new int[width][width];
        for (int i = 0; i < a.length; i++) {
            int row = i / width;
            int col = i % width;
            b[row][col] = a[i];
        }
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] test = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

        Board testBoard = new Board(test);
        System.out.println(testBoard);

        System.out.println("Is it goal board? " + testBoard.isGoal());
        System.out.println("Manhattan: " + testBoard.manhattan());
        System.out.println("Hamming: " + testBoard.hamming());
        System.out.println();

        Board goalBoard = new Board(goal);
        System.out.println(goalBoard);
        System.out.println("Is it goal board? " + goalBoard.isGoal());
        System.out.println("Manhattan: " + goalBoard.manhattan());
        System.out.println("Hamming: " + goalBoard.hamming());
        System.out.println();

        int n = 1;
        Iterable<Board> neighbors = testBoard.neighbors();
        for (Board b : neighbors) {
            System.out.println("Neighbour : " + n++);
            System.out.println(b);
        }

        Board twin;
        twin = testBoard.twin();
        System.out.println("Twin");
        System.out.println(twin);

        Board boardClone = new Board(test);
        System.out.println("Is board equal to twin? " + testBoard.equals(twin));
        System.out.println("Is board equal to boardClone? " + testBoard.equals(boardClone));
    }
}
