package edu.aitu.daa.algo;
import edu.aitu.daa.util.Metrics;

import java.util.Arrays;
import java.util.Comparator;
public class ClosestPair {
    public record Point(double x, double y) {}
    public static double closest(Point[] pts, Metrics m) {
        Point[] byX = Arrays.copyOf(pts, pts.length);
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        if (m != null) m.incAllocations();
        Point[] temp = new Point[pts.length];
        if (m != null) m.incAllocations();
        return closest(byX, temp, 0, pts.length - 1, m);}

    private static double closest(Point[] byX, Point[] temp, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = lo; i <= hi; i++) {
                for (int j = i + 1; j <= hi; j++) {
                    double dist = dist(byX[i], byX[j]);
                    if (m != null) m.incComparisons();
                    if (dist < d) d = dist;}}
            Arrays.sort(byX, lo, hi + 1, Comparator.comparingDouble(p -> p.y));
            return d;}

        int mid = lo + (hi - lo) / 2;
        double midX = byX[mid].x;
        double dl;
        double dr;
        if (m != null) m.enter();
        dl = closest(byX, temp, lo, mid, m);
        dr = closest(byX, temp, mid + 1, hi, m);
        if (m != null) m.exit();
        double d = Math.min(dl, dr);
        mergeByY(byX, temp, lo, mid, hi);
        int sz = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(byX[i].x - midX) < d) {
                temp[sz++] = byX[i];}}
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz && (temp[j].y - temp[i].y) < d; j++) {
                double dist = dist(temp[i], temp[j]);
                if (m != null) m.incComparisons();
                if (dist < d) d = dist;}}
        return d;
    }
    private static void mergeByY(Point[] a, Point[] aux, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (aux[i].y <= aux[j].y) a[k++] = aux[i++];
            else a[k++] = aux[j++];
        }
        while (i <= mid) a[k++] = aux[i++];
        while (j <= hi) a[k++] = aux[j++];
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
