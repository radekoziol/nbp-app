package currency.statistics;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public class ListStats {

    private DoubleSummaryStatistics getSummaryStatistics (List<?> records, String method){

        return records.stream()
                .mapToDouble(r -> {
            try {
                return (double) r.getClass()
                        .getMethod(method)
                        .invoke(r);
                } catch (IllegalAccessException e) {
                    System.err.println("Method does not have access to the definition of " +
                            " the specified class, field, method or constructor");
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    System.err.println("There no such method! ");
                    e.printStackTrace();
                }
        return -1;
        }).summaryStatistics();

    }

    protected Double getStandardDeviation(List<Object> records, String method ) throws NoSuchMethodException,IOException,IllegalArgumentException {

        double average = getAverageOf(records,method);

        List<Object> standardDeviations = new LinkedList<>();

        records.stream()
                .forEach(r ->
                        {
                            try {
                                standardDeviations.add(Math.pow(average - (Double) r.getClass()
                                .getMethod(method).invoke(r), 2));
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                );

        return Math.sqrt(getSummaryStatistics(records,method).getSum()/(records.size() - 1) );

    }

    protected double getAverageOf(List<?> records, String method) {
        return getSummaryStatistics(records,method).getAverage();
    }

    protected <T> T  getMinOf(List<T> records, Function<T,Double> method){

        return records.stream()
                .min(Comparator.comparing(method))
                .get();

    }

    protected <T> T getMaxOf(List<T> records, Function<T,Double> method){

        return records.stream()
                .max(Comparator.comparing(method))
                .get();
    }

}
