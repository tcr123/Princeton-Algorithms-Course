import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final double XMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMIN = 0.0;
    private static final double YMAX = 1.0;

    private int size;
    private Node root;

    private class Node {
        private Node left;
        private Node right;
        private Point2D value;
        private RectHV rect;

        private Node(Point2D p, RectHV inRect) {
            left = null;
            right = null;
            value = p;
            rect = inRect;
        }
    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int compare(Point2D newPoint, Point2D p, int level) {
        if (level % 2 == 0) {
            int cmp = Double.compare(newPoint.x(), p.x());
            if (cmp == 0) {
                return Double.compare(newPoint.y(), p.y());
            }
            return cmp;
        }
        else {
            int cmp = Double.compare(newPoint.y(), p.y());
            if (cmp == 0) {
                return Double.compare(newPoint.x(), p.x());
            }
            return cmp;
        }
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (!contains(p))
            root = insert(root, p, XMIN, YMIN, XMAX, YMAX, 0);
    }

    private Node insert(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax,
                        int level) {
        if (x == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        int cmp = compare(p, x.value, level);
        if (cmp < 0) {
            if (level % 2 == 0) {
                x.left = insert(x.left, p, xmin, ymin, x.value.x(), ymax, level + 1);
            }
            else {
                x.left = insert(x.left, p, xmin, ymin, xmax, x.value.y(), level + 1);
            }
        }
        else {
            if (level % 2 == 0) {
                x.right = insert(x.right, p, x.value.x(), ymin, xmax, ymax, level + 1);
            }
            else {
                x.right = insert(x.right, p, xmin, x.value.y(), xmax, ymax, level + 1);
            }
        }

        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return contain(root, p, 0) != null;
    }

    private Point2D contain(Node x, Point2D p, int level) {
        if (x != null) {
            int cmp = compare(p, x.value, level);
            if (cmp < 0) {
                return contain(x.left, p, level + 1);
            }
            else if (cmp > 0) {
                return contain(x.right, p, level + 1);
            }
            else {
                return x.value;
            }
        }

        return null;
    }

    public void draw() {
        StdDraw.clear();

        drawLine(root, 0);
    }

    private void drawLine(Node x, int level) {
        if (x != null) {
            drawLine(x.left, level + 1);

            StdDraw.setPenRadius();
            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.value.x(), x.rect.ymin(), x.value.x(), x.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.value.y(), x.rect.xmax(), x.value.y());
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.value.draw();

            drawLine(x.right, level + 1);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("");
        Queue<Point2D> listOfPoint = new Queue<Point2D>();

        addPoint(root, rect, listOfPoint);

        return listOfPoint;
    }

    private void addPoint(Node x, RectHV rect, Queue<Point2D> listOfPoint) {
        if (x != null && rect.intersects(x.rect)) {
            if (rect.contains(x.value))
                listOfPoint.enqueue(x.value);

            addPoint(x.left, rect, listOfPoint);
            addPoint(x.right, rect, listOfPoint);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (isEmpty()) return null;
        else {
            Point2D minPoint = root.value;

            minPoint = nearestP(root, p, minPoint, 0);

            return minPoint;
        }
    }

    private Point2D nearestP(Node x, Point2D p, Point2D minPoint, int level) {
        Point2D min = minPoint;

        if (x == null) return min;
        if (p.distanceSquaredTo(x.value) < p.distanceSquaredTo(min))
            min = x.value;

        if (level % 2 == 0) {
            if (x.value.x() < p.x()) {
                min = nearestP(x.right, p, min, level + 1);
                if (x.left != null && min.distanceSquaredTo(p) > x.left.rect.distanceSquaredTo(p)) {
                    min = nearestP(x.left, p, min, level + 1);
                }
            }
            else {
                min = nearestP(x.left, p, min, level + 1);
                if (x.right != null && min.distanceSquaredTo(p) > x.right.rect
                        .distanceSquaredTo(p)) {
                    min = nearestP(x.right, p, min, level + 1);
                }
            }
        }
        else {
            if (x.value.y() < p.y()) {
                min = nearestP(x.right, p, min, level + 1);
                if (x.left != null && min.distanceSquaredTo(p) > x.left.rect.distanceSquaredTo(p)) {
                    min = nearestP(x.left, p, min, level + 1);
                }
            }
            else {
                min = nearestP(x.left, p, min, level + 1);
                if (x.right != null && min.distanceSquaredTo(p) > x.right.rect
                        .distanceSquaredTo(p)) {
                    min = nearestP(x.right, p, min, level + 1);
                }
            }
        }

        return min;
    }

    public static void main(String[] args) {

    }
}
