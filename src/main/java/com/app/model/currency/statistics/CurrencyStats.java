package com.app.model.currency.statistics;

import com.app.model.api.date.Date;
import com.app.model.api.query.CurrencyQuery;
import com.app.model.api.query.request.CurrencyRequest;
import com.app.model.api.query.request.Request;
import com.app.model.currency.Table;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * This class is has in details currency statistical methods
 */

public class CurrencyStats extends ListStats {


    public String getMostVolatileCurrency(Date from, Date to) throws IOException, InterruptedException {

        List<Table> rates = createListOfAllCurrenciesRates(from, to);

        return getCurrencyWithMaxDeviationOutOfRates(rates);
    }

    private String getCurrencyWithMaxDeviationOutOfRates(List<Table> rates) {

        // Setting variable to be changed for maximum
        Pair<String, Double> maxCurrencyDeviation = new Pair<>("", -0.1);

        for (Table currencyTable : rates) {
            Double deviationValue = getStandardDeviationForGivenRates(currencyTable.getRates());
            Pair<String, Double> currencyDeviation = new Pair<>(currencyTable.getCurrency(), deviationValue);

            maxCurrencyDeviation = setNewMaxDeviationIfNecessary(currencyDeviation,maxCurrencyDeviation);
        }

        return maxCurrencyDeviation.getKey();
    }

    private Pair<String, Double> setNewMaxDeviationIfNecessary(Pair<String, Double> iterator, Pair<String, Double> maxDeviation) {

        if (maxDeviation.getValue() < iterator.getValue())
            return iterator;
        else
            return maxDeviation;
    }

    private Double getStandardDeviationForGivenRates(List<Table.Rates> rates) {

        // Calculation based on bid price
        Function<Table.Rates, Double> getter = Table.Rates::getBid;

        return super.getStandardDeviation(rates, getter);
    }

    private List<Table> createListOfAllCurrenciesRates(Date from, Date to) throws IOException, InterruptedException {

        List<Table> rates = new ArrayList<>();

        for (Map.Entry<String, String> entry : Table.Rates.codes.entrySet()) {
            String currency = entry.getKey();
            List<Table> ratesForCurrency = getRatesForCurrency(new Pair<>(from, to), currency);
            rates.addAll(ratesForCurrency);
        }

        return rates;
    }

    private List<Table> getRatesForCurrency(Pair<Date, Date> datesFromTo, String currency) throws IOException, InterruptedException {

        Request request = CurrencyRequest.createRequestForExchangeRatesForCurrency(datesFromTo, currency);
        CurrencyQuery currencyQuery = new CurrencyQuery();

        return currencyQuery
                .getAllObjectsFrom(request);
    }


/*
    public Map<String, Double> getAverageRateOf(List<Table> currencies, Function<Table.Rates, Double> getter) {

        Double[] averageExchangeRate = new Double[currencies.get(0).getRates().size()];
        Arrays.fill(averageExchangeRate, 0d);

        for (Table currency : currencies) {
            int counter = 0;
            for (Table.Rates rate0 : currency.getRates()) {
                averageExchangeRate[counter] += getter.apply(rate0);
                counter++;
            }
        }

        //Filling averageExchangeRate to map
        Map<String, Double> map = new HashMap<>();
        for (Table currency : currencies) {
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
/*

    /**
     * Returns minimum rate of given currencies
     *
     * @param currencies
     * @param getter
     * @return
     */
    public Table.Rates getMinRateOf(List<Table.Rates> currencies, Function<Table.Rates, Double> getter) {
        return super.getMinOf(currencies, getter);
    }

    /**
     * Returns maximum rate of given currencies
     *
     * @param currencies
     * @param getter
     * @return
     */
    public Table.Rates getMaxRateOf(List<Table.Rates> currencies, Function<Table.Rates, Double> getter) {
        return super.getMaxOf(currencies, getter);
    }

}

