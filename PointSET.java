import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> ts1;

    public PointSET() {
        ts1 = new TreeSet<>();
    }

    public boolean isEmpty() {
        return ts1.isEmpty();
    }

    public int size() {
        return ts1.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        ts1.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return ts1.contains(p);
    }

    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        
        for (Point2D point : ts1) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("");
        Queue<Point2D> listOfPoint = new Queue<Point2D>();

        for (Point2D point : ts1) {
            if (rect.contains(point))
                listOfPoint.enqueue(point);
        }

        return listOfPoint;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (isEmpty()) return null;
        else {
            Point2D minPoint = null;
            for (Point2D point : ts1) {
                if (minPoint == null || p.distanceSquaredTo(point) < p.distanceSquaredTo(minPoint))
                    minPoint = point;
            }
            return minPoint;
        }
    }

    public static void main(String[] args) {

    }
}
