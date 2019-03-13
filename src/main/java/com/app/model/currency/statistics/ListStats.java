package com.app.model.currency.statistics;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is more general class for dealing with statistics.
 */
public class ListStats {


    /**
     * Returns average of list with given method
     * @param records list of objects
     * @param method method that returns Double
     * @param <T>
     * @return
     */
    protected <T> Double getAverageOf(List<T> records, Function<T,Double> method) {

        return records.stream()
                .mapToDouble(method::apply)
                .average().orElse(0);
    }

    /**
     * Returns minimum element of given list
     * @param records list of objects
     * @param method method that returns Double
     * @param <T>
     * @return
     */
    protected <T> T  getMinOf(List<T> records, Function<T,Double> method){

        return Optional.ofNullable(records)
                .orElse(Collections.emptyList())
                .stream()
                .min(Comparator.comparing(method))
                .get();

    }

    /**
     * Returns maximum element of given list
     * @param records list of objects
     * @param method method that returns Double
     * @param <T>
     * @return
     */
    protected <T> T getMaxOf(List<T> records, Function<T,Double> method){

        return records.stream()
                .max(Comparator.comparing(method))
                .get();
    }

    protected <T> Double getStandardDeviation(List<T> data, Function<T,Double> method) {

        Double deviationSquareSum = calculateDeviationSquareSum(data,method);

        return deviationSquareSum/(data.size()-1);
    }

    private <T> Double calculateDeviationSquareSum(List<T> data, Function<T, Double> method) {

        Double average = getAverageOf(data,method);

        List<Double> deviationSquares =
                data.stream()
                        .map(d -> Math.pow(method.apply(d) - average,2))
                        .collect(Collectors.toList());

        return deviationSquares.stream().mapToDouble(f -> f).sum();
    }

}
