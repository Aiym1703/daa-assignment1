package edu.aitu.daa.algo;
import edu.aitu.daa.util.ArrayUtil;
import edu.aitu.daa.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
public class DeterministicSelectTest {

    @Test
    void matchesSortedAcrossTrials() {
        Random r = ThreadLocalRandom.current();
        int trials = 100;
        int n = 1000;
        for (int t = 0; t < trials; t++) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt(10_000);
            int k = r.nextInt(n);
            int[] b = Arrays.copyOf(a, n);
            Arrays.sort(b);
            Metrics m = new Metrics();
            int val = DeterministicSelect.select(a, k, m);
            assertEquals(b[k], val);
        }
    }
}
