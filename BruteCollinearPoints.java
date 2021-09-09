import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segment;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("");
        }

        Point[] copy = points.clone();
        Arrays.sort(copy);

        if (copy.length > 1) {
            for (int i = 1; i < points.length; i++) {
                if (copy[i].compareTo(copy[i - 1]) == 0) throw new IllegalArgumentException("");
            }
        }

        ArrayList<LineSegment> result = new ArrayList<LineSegment>();

        if (copy.length > 3) {
            Point[] t = new Point[4];
            for (int a = 0; a < copy.length - 3; a++) {
                t[0] = copy[a];
                for (int b = a + 1; b < copy.length - 2; b++) {
                    t[1] = copy[b];
                    for (int c = b + 1; c < copy.length - 1; c++) {
                        t[2] = copy[c];
                        for (int d = c + 1; d < copy.length; d++) {
                            t[3] = copy[d];
                            if (isthatColl(t)) {
                                LineSegment line = getLine(t);
                                result.add(line);
                            }
                        }
                    }
                }
            }
        }

        segment = result.toArray(new LineSegment[result.size()]);
    }

    public int numberOfSegments() {
        return segment.length;
    }     // the number of line segments

    public LineSegment[] segments() {
        return segment.clone();
    }          // the line segments

    private LineSegment getLine(Point[] points) {
        Arrays.sort(points);
        return new LineSegment(points[0], points[3]);
    }

    private boolean isthatColl(Point[] points) {
        double slope1 = points[0].slopeTo(points[1]);
        double slope2 = points[0].slopeTo(points[2]);
        double slope3 = points[0].slopeTo(points[3]);
        if ((slope1 == slope2) && (slope1 == slope3))
            return true;
        return false;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
