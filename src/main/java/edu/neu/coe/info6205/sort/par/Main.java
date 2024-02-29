    package edu.neu.coe.info6205.sort.par;

    import java.io.BufferedWriter;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.OutputStreamWriter;
    import java.text.DecimalFormat;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Random;
    import java.util.concurrent.ForkJoinPool;

    /**
     * This code has been fleshed out by Ziyao Qiao. Thanks very much.
     * CONSIDER tidy it up a bit.
     */
    public class Main {

        public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        public static void main(String[] args) {
            System.out.println("Number of processors: " + Runtime.getRuntime().availableProcessors());
            int noofProcessors = Runtime.getRuntime().availableProcessors();
            for(int th = 2; th <= noofProcessors; th *= 2) {
                for(int runs = 1, arraySize = 500000; runs <= 3; runs++, arraySize *=2) {
                    threadBenchmark(arraySize, th);
                }
            }
        }

        private static void threadBenchmark(int arraySize, int threads) {
            String fileName = "./src/Benchmark_ArraySize_" + arraySize + "_Threads_" + threads + ".csv";
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
                writer.write("Cutoff Ratio,Time (ms)\n");
                System.out.println("Benchmarking with Array Size: " + arraySize + " and Threads: " + threads);
                for (double cutoffRatio = 0.05; cutoffRatio <= 1.00; cutoffRatio += 0.05) {
                    long finalTime = sortBenchmark(arraySize, threads, cutoffRatio);
                    System.out.println("Cutoff Ratio: " + decimalFormat.format(cutoffRatio) + " Time: " + finalTime + "ms");
                    writer.write(decimalFormat.format(cutoffRatio) + "," + finalTime + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static long sortBenchmark(int arraySize, int threads, double cutoffRatio) {
            ParSort.cutoff = (int) (arraySize * cutoffRatio);
            Random random = new Random();
            int[] array = new int[arraySize];
            ParSort.forkJoinPool = new ForkJoinPool(threads);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) {
                    array[i] = random.nextInt(10000000);
                }
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            return time;
        }


        private static void processArgs(String[] args) {
            String[] xs = args;
            while (xs.length > 0)
                if (xs[0].startsWith("-")) xs = processArg(xs);
        }

        private static String[] processArg(String[] xs) {
            String[] result = new String[0];
            System.arraycopy(xs, 2, result, 0, xs.length - 2);
            processCommand(xs[0], xs[1]);
            return result;
        }

        private static void processCommand(String x, String y) {
            if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
            else
                // TODO sort this out
                if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                    ForkJoinPool.getCommonPoolParallelism();
        }

        private static void setConfig(String x, int i) {
            configuration.put(x, i);
        }

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private static final Map<String, Integer> configuration = new HashMap<>();


    }