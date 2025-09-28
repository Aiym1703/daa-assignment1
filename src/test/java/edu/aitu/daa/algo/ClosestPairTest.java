package edu.aitu.daa.algo;

import edu.aitu.daa.algo.ClosestPair.Point;
import edu.aitu.daa.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    private static double naive(Point[] pts) {
        double d = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i+1; j < pts.length; j++) {
                double dx = pts[i].x() - pts[j].x();
                double dy = pts[i].y() - pts[j].y();
                double dist = Math.hypot(dx, dy);
                if (dist < d) d = dist;
            }
        }
        return d;
    }

    @Test
    void validatesAgainstNaive() {
        Random r = ThreadLocalRandom.current();
        int n = 1000;
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) pts[i] = new Point(r.nextDouble(), r.nextDouble());
        Metrics m = new Metrics();
        double fast = ClosestPair.closest(pts, m);
        double slow = naive(pts);
        assertEquals(slow, fast, 1e-9);
    }
}
