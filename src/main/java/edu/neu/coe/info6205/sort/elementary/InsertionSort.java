/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.HelperFactory;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Config;

import java.util.Random;

/**
 * Class InsertionSort.
 *
 * @param <X> the underlying comparable type.
 */
public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        // TO BE IMPLEMENTED
        for (int i = from + 1; i < to; i++) {
            for (int j = i; j > from; j--) {
                if (helper.less(xs[j], xs[j - 1])) {
                    helper.swap(xs,j - 1, j);
                } else break;
            }
        }
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }

    public static void main(String[] args) {
        int n = 500;
        int maxN = 16000;
        Random random = new Random();

        while (n <= maxN) {
            Integer[] randomArray = new Integer[n];
            Integer[] orderedArray = new Integer[n];
            Integer[] partiallyOrderedArray = new Integer[n];
            Integer[] reverseOrderedArray = new Integer[n];

            for (int i = 0; i < n; i++) {
                randomArray[i] = random.nextInt(n);
                orderedArray[i] = i;
                partiallyOrderedArray[i] = (i % 2 == 0) ? i : (i - 1);
                reverseOrderedArray[i] = n - i;
            }
            for (int i = 1; i < n; i++) {
                int swapWithIndex = random.nextInt(i + 1);
                int temp = partiallyOrderedArray[i];
                partiallyOrderedArray[i] = partiallyOrderedArray[swapWithIndex];
                partiallyOrderedArray[swapWithIndex] = temp;
            }
            measureSortTime("Random Array", randomArray, n);
            measureSortTime("Ordered Array", orderedArray, n);
            measureSortTime("Partially Ordered Array", partiallyOrderedArray, n);
            measureSortTime("Reverse Ordered Array", reverseOrderedArray, n);
            n *= 2;
        }
    }

    private static void measureSortTime(String description, Integer[] array, int n) {
        final Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
        helper.init(n);
        SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);
        long startTime = System.nanoTime();
        sorter.preProcess(array);
        Integer[] finalArray = sorter.sort(array);
        sorter.postProcess(finalArray);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(description + ": n=" + n + ", time=" + duration + "ms");
    }
}