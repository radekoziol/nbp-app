package currency.statistics;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

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

        return records.stream()
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

}
