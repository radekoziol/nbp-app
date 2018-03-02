package currency.statistics;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    public Double getStandardDeviation(List<Object> records, String method ) throws NoSuchMethodException,IOException,IllegalArgumentException {

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

    public double getAverageOf(List<?> records, String method) {
        return getSummaryStatistics(records,method).getAverage();
    }

    public Object getMinOf(List<Object> records, String method){

        return records.stream()
                .filter(r ->
                        r.equals(
                                getSummaryStatistics(records,method).getMin()));

    }

    public Object getMaxOf(List<Object> records, String method){

        return records.stream()
                .filter(r ->
                        r.equals(
                                getSummaryStatistics(records,method)
                                        .getMax()));

    }

}
