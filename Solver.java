import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private SearchNode lastNode;
    private boolean isSolve;
    private int minMoves = 0;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("");

        int moves = 0;
        int twinMoves = 0;

        Queue<Board> neighbours = new Queue<Board>();
        Queue<Board> twinNeighbours = new Queue<Board>();

        MinPQ<SearchNode> searchNode = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinSearchNode = new MinPQ<SearchNode>();

        SearchNode node = new SearchNode(initial, moves, null);
        SearchNode twinNode = new SearchNode(initial.twin(), twinMoves, null);

        searchNode.insert(node);
        twinSearchNode.insert(twinNode);

        boolean solvableBoard = false;
        boolean twinSolvableBoard = false;

        SearchNode current = null;

        while (!solvableBoard && !twinSolvableBoard) {
            current = searchNode.delMin();
            SearchNode previousNode = current.getPreNode();
            Board temp = current.getBoard();
            solvableBoard = temp.isGoal();

            SearchNode currentTwin = twinSearchNode.delMin();
            SearchNode previousTwinNode = currentTwin.getPreNode();
            Board tempTwin = currentTwin.getBoard();
            twinSolvableBoard = tempTwin.isGoal();

            for (Board board : temp.neighbors()) {
                neighbours.enqueue(board);
            }

            for (Board board : tempTwin.neighbors()) {
                twinNeighbours.enqueue(board);
            }

            while (neighbours.size() > 0) {
                Board board = neighbours.dequeue();
                int movesTemp = current.getMoves();
                movesTemp++;
                if (previousNode != null && board.equals(previousNode.getBoard()))
                    continue;
                SearchNode newNode = new SearchNode(board, movesTemp, current);
                searchNode.insert(newNode);
            }

            while (twinNeighbours.size() > 0) {
                Board board = twinNeighbours.dequeue();
                int movesTemp = currentTwin.getMoves();
                movesTemp++;
                if (previousTwinNode != null && board.equals(previousTwinNode.getBoard()))
                    continue;
                SearchNode newNode = new SearchNode(board, movesTemp, currentTwin);
                twinSearchNode.insert(newNode);
            }

            moves = current.getMoves();
            twinMoves = currentTwin.getMoves();
            lastNode = current;
        }

        isSolve = !twinSolvableBoard;
        minMoves = moves;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolve;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> listOfAnswer = new Stack<Board>();
        SearchNode node = this.lastNode;
        if (this.isSolvable()) {
            while (node.getPreNode() != null) {
                listOfAnswer.push(node.getBoard());
                node = node.getPreNode();
            }
            listOfAnswer.push(node.getBoard());

            return listOfAnswer;
        }

        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode previous = null;
        private Board initial = null;
        private int moves = 0;
        private int priority = 0;

        public SearchNode(Board a, int m, SearchNode pre) {
            initial = a;
            moves = m;
            previous = pre;

            priority = initial.manhattan() + moves;
        }

        private int getMoves() {
            return moves;
        }

        private Board getBoard() {
            Board temp = initial;
            return temp;
        }

        private SearchNode getPreNode() {
            SearchNode temp = previous;
            return temp;
        }

        private int getPriority() {
            return priority;
        }

        public int compareTo(SearchNode a) {
            return this.getPriority() - a.getPriority();
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
