package statistics;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class ListStats {

    private DoubleSummaryStatistics getSummaryStatistics (List<Object> records, Function<Object, Double> getValue ){

        return records.stream().mapToDouble(r -> {
            try {
                return r.getClass().getMethod(String.valueOf(getValue));
            } catch (NoSuchMethodException e) {
                System.out.println("There is no ");
                e.printStackTrace();
            }
        }).summaryStatistics();
    }

    public Object getMostVolatileRecord(List<Object> records, Function<Object, Double> getValue ) throws IOException,IllegalArgumentException {

        return null;

    }

    public double getAverageOf(List<Object> records, Function<Object, Double> getValue) {
        return getSummaryStatistics(records,getValue).getAverage();
    }

    public Object getMinOf(List<Object> records, Function<Object, Double> getValue){

        return records.stream()
                .filter(r ->
                        r.equals(
                                getSummaryStatistics(records,getValue).getMin()));

    }

    public Object getMaxOf(List<Object> records, Function<Object, Double> getValue){

        return records.stream()
                .filter(r ->
                        r.equals(
                                getSummaryStatistics(records,getValue).getMax()));

    }

}
