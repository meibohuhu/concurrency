package stream;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelStream {
    public static void main(String[] args) {
//        poolTest();
        parallelStreamTest();
    }

    // arallel streams use a common ForkJoinPool available via the static ForkJoinPool.commonPool() method. The size of the underlying thread-pool uses up to five threads - depending on the amount of available physical CPU cores:
    public static void poolTest() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());    // 3
    }

    //  The output may differ in consecutive runs because the behavior which particular thread is actually used is non-deterministic.
    public static void parallelStreamTest() {
        // Main, thread1, thread2, thread3, thread4
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
    }
    /*
filter: b1 [main]
filter: a2 [ForkJoinPool.commonPool-worker-1]
map: a2 [ForkJoinPool.commonPool-worker-1]
forEach: A2 [ForkJoinPool.commonPool-worker-1]
filter: c2 [ForkJoinPool.commonPool-worker-4]
map: c2 [ForkJoinPool.commonPool-worker-4]
forEach: C2 [ForkJoinPool.commonPool-worker-4]
filter: c1 [ForkJoinPool.commonPool-worker-2]
map: c1 [ForkJoinPool.commonPool-worker-2]
forEach: C1 [ForkJoinPool.commonPool-worker-2]
filter: a1 [ForkJoinPool.commonPool-worker-3]
map: a1 [ForkJoinPool.commonPool-worker-3]
forEach: A1 [ForkJoinPool.commonPool-worker-3]
map: b1 [main]
forEach: B1 [main]
     */


}
