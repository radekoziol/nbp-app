package currency.statistics;

import api.date.Date;
import currency.Table;

import java.util.*;
import java.util.function.Function;

/**
 * This class is has in details currency statistical methods
 */

public class CurrencyStats extends ListStats {


    /**
     * Returns most volatile currency from given period
     * @param startDate
     * @param endDate
     * @return
     */
    public Currency getMostVolatileCurrency(Date startDate, Date endDate){

        //TODO
        System.out.println("This is not working yet!");
        return null;
    }

    /**
     * Returns average rate of given list of currencies
     * @param currencies
     * @param getter method returning Double
     * @return
     */
    public Map<String, Double> getAverageRateOf(List<currency.Table> currencies, Function<Table.Rates, Double> getter) {

        Double[] averageExchangeRate = new Double[currencies.get(0).getRates().size()];
        Arrays.fill(averageExchangeRate, 0d);

        for (currency.Table currency : currencies) {
            int counter = 0;
            for (currency.Table.Rates rate0 : currency.getRates()) {
                averageExchangeRate[counter] += getter.apply(rate0);
                counter++;
            }
        }

        //Filling averageExchangeRate to map
        Map<String, Double> map = new HashMap<>();
        for (currency.Table currency : currencies) {
            int counter = 0;
            for (Table.Rates rate4 : currency.getRates()) {
                //Average is really below calculated
                averageExchangeRate[counter] /= currency.getRates().size();
                map.put(rate4.getCurrency(), averageExchangeRate[counter]);
                counter++;
            }
            break;
        }

        return map;
    }

    /**
     * Returns minimum rate of given currencies
     * @param currencies
     * @param getter
     * @return
     */
    public Table.Rates getMinRateOf(List<Table.Rates> currencies, Function<Table.Rates,Double> getter) {
        return super.getMinOf(currencies, getter);
    }

    /**
     * Returns maximum rate of given currencies
     * @param currencies
     * @param getter
     * @return
     */
    public Table.Rates getMaxRateOf(List<Table.Rates> currencies, Function<Table.Rates,Double> getter) {
        return super.getMaxOf(currencies, getter);
    }

}

