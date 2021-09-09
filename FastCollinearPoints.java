/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segment;

    public FastCollinearPoints(Point[] points) {
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
            Point[] temp = copy.clone();
            for (int i = 0; i < copy.length; i++) {
                Arrays.sort(temp, copy[i].slopeOrder());
                findsegment(temp, copy[i], result);
            }
        }

        segment = result.toArray(new LineSegment[result.size()]);
    }  // finds all line segments containing 4 or more points

    private void findsegment(Point[] sortedp, Point p, ArrayList<LineSegment> result) {
        double val = p.slopeTo(sortedp[1]);
        int slength = 1;
        for (int i = 2; i < sortedp.length; i++) {
            double val2 = p.slopeTo(sortedp[i]);
            if (!isthatColl(val, val2)) {
                if (i - slength >= 3) {
                    Point[] points = getSegment(sortedp, p, slength, i);

                    if (points[0] == p) {
                        result.add(new LineSegment(points[0], points[1]));
                    }
                }

                slength = i;
                val = val2;
            }
        }

        if (sortedp.length - slength >= 3) {
            Point[] points = getSegment(sortedp, p, slength, sortedp.length);

            if (points[0] == p) {
                result.add(new LineSegment(points[0], points[1]));
            }
        }
    }

    private boolean isthatColl(double p1slope, double p2slope) {
        if (Double.compare(p1slope, p2slope) == 0)
            return true;
        return false;
    }

    private Point[] getSegment(Point[] points, Point p, int start, int end) {
        ArrayList<Point> linepoint = new ArrayList<Point>();
        linepoint.add(p);
        for (int i = start; i < end; i++) {
            linepoint.add(points[i]);
        }
        linepoint.sort(null);
        return new Point[] { linepoint.get(0), linepoint.get(linepoint.size() - 1) };
    }

    public int numberOfSegments() {
        return segment.length;
    }     // the number of line segments

    public LineSegment[] segments() {
        return segment.clone();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
