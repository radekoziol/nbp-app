package currency.statistics;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class ListStats {


    protected <T> Double getAverageOf(List<T> records, Function<T,Double> method) {

        return records.stream()
                .mapToDouble(method::apply)
                .average().orElse(0);
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
