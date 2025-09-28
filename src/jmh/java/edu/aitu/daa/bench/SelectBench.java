package edu.aitu.daa.bench;

import edu.aitu.daa.algo.DeterministicSelect;
import edu.aitu.daa.util.Metrics;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
public class SelectBench {

    @State(Scope.Benchmark)
    public static class Data {
        @Param({"10000","50000"})
        public int n;
        int[] a;
        Random r;

        @Setup(Level.Invocation)
        public void setup() {
            r = ThreadLocalRandom.current();
            a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt();
        }
    }

    @Benchmark
    public int deterministicSelect(Data d) {
        int k = d.n / 2;
        return DeterministicSelect.select(Arrays.copyOf(d.a, d.a.length), k, new Metrics());
    }

    @Benchmark
    public int sortThenPick(Data d) {
        int[] b = Arrays.copyOf(d.a, d.a.length);
        Arrays.sort(b);
        return b[d.n / 2];
    }
}
