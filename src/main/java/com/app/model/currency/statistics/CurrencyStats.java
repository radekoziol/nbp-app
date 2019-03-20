package com.app.model.currency.statistics;

import com.app.model.api.date.Date;
import com.app.model.api.request.Request;
import com.app.model.api.request.RequestExecutor;
import com.app.model.api.request.currency.CurrencyRequest;
import com.app.model.api.request.currency.CurrencyRequestExecutor;
import com.app.model.api.request.currency.CurrencyRequestValidator;
import com.app.model.currency.Table;
import org.springframework.data.util.Pair;

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
        Pair<String, Double> maxCurrencyDeviation = Pair.of("", -0.1);

        for (Table currencyTable : rates) {
            Double deviationValue = getStandardDeviationForGivenRates(currencyTable.getRates());
            Pair<String, Double> currencyDeviation = Pair.of(currencyTable.getCurrency(), deviationValue);

            maxCurrencyDeviation = setNewMaxDeviationIfNecessary(currencyDeviation, maxCurrencyDeviation);
        }

        return maxCurrencyDeviation.getFirst();
    }

    private Pair<String, Double> setNewMaxDeviationIfNecessary(Pair<String, Double> iterator, Pair<String, Double> maxDeviation) {

        if (maxDeviation.getSecond() < iterator.getSecond())
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
            List<Table> ratesForCurrency = getRatesForCurrency(Pair.of(from, to), currency);
            rates.addAll(ratesForCurrency);
        }

        return rates;
    }

    private List<Table> getRatesForCurrency(Pair<Date, Date> datesFromTo, String currency) throws IOException, InterruptedException {

        Request request = CurrencyRequest.createRequestForExchangeRatesForCurrency(datesFromTo, currency);
        CurrencyRequestExecutor requestExecutor = new CurrencyRequestExecutor(request);

        return requestExecutor.getAllObjectsFromRequest();
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
         */
    public Table.Rates getMinRateOf(List<Table.Rates> currencies, Function<Table.Rates, Double> getter) {
        return super.getMinOf(currencies, getter);
    }

    /**
     * Returns maximum rate of given currencies
     */
    public Table.Rates getMaxRateOf(List<Table.Rates> currencies, Function<Table.Rates, Double> getter) {
        return super.getMaxOf(currencies, getter);
    }

    public double getCurrencyPrice(Date date, String currency) throws IOException, InterruptedException {

        Request request = CurrencyRequest.createRequestForCurrencyPrice(date, currency);
        CurrencyRequestExecutor requestExecutor = new CurrencyRequestExecutor(request);

        return getCurrencyPriceFromRequestResponse(requestExecutor);
    }

    private double getCurrencyPriceFromRequestResponse(RequestExecutor<Table> requestExecutor) throws IOException, InterruptedException {

        Table table = (Table) requestExecutor
                .getAllObjectsFromRequest()
                .get(0);

        return table.getRates().get(0).getAsk();

    }

    public Pair<String, Double> getMinBidPrice(Date date) throws IOException, InterruptedException {

        Request request = CurrencyRequest.createRequestForExchangeRatesForCurrencies(date);
        CurrencyRequestExecutor requestExecutor = new CurrencyRequestExecutor(request);

        List<Table[]> rates = requestExecutor.getAllObjectsFromRequest();
        CurrencyStats currencyStats = new CurrencyStats();

        Table.Rates rate = currencyStats.getMinRateOf(rates.get(0)[0].getRates(), Table.Rates::getBid);

        return Pair.of(rate.getCurrency(), rate.getBid());

    }

    public Pair<Pair<Date, Double>, Pair<Date, Double>> getDatesWhenCurrencyWasMostAndLeastExpensive(String currency) throws IOException, InterruptedException {

        Request request = CurrencyRequest.createRequestForExchangeRatesForCurrency
                (Pair.of(CurrencyRequestValidator.oldestDate, Date.getCurrentDate()), currency);

        CurrencyRequestExecutor requestExecutor = new CurrencyRequestExecutor(request);
        List<Table> allTables = requestExecutor.getAllObjectsFromRequest();

        Pair<Table.Rates, Table.Rates> minAndMax = calculateWhenCurrencyWasMostAndLeastExpensive(allTables);
        Pair<Date, Double> min = Pair.of(new Date(minAndMax.getFirst().getEffectiveDate()), minAndMax.getFirst().getBid());
        Pair<Date, Double> max = Pair.of(new Date(minAndMax.getSecond().getEffectiveDate()), minAndMax.getSecond().getBid());

        return Pair.of(min, max);
    }

    private Pair<Table.Rates, Table.Rates> calculateWhenCurrencyWasMostAndLeastExpensive(List<Table> allTables) {

        List<Table.Rates> allRates = new ArrayList<>();
        allTables
                .forEach(t -> allRates.addAll(t.getRates()));

        CurrencyStats currencyStats = new CurrencyStats();
        Table.Rates min = currencyStats.getMinRateOf(allRates, Table.Rates::getBid);
        Table.Rates max = currencyStats.getMaxRateOf(allRates, Table.Rates::getBid);

        return Pair.of(min, max);
    }

    public Map<String, Double> getSortedListOfBidPrice(Date date) throws IOException, InterruptedException {

        Request request = CurrencyRequest.createRequestForExchangeRatesForCurrencies(date);

        CurrencyRequestExecutor currencyRequestExecutor = new CurrencyRequestExecutor(request);

        List<Table[]> currencies = currencyRequestExecutor.getAllObjectsFromRequest();
        Table list = getSortedTable(currencies);

        return createOutputMapOutOfSortedTable(list);
    }

    private Map<String, Double> createOutputMapOutOfSortedTable(Table list) {

        Map<String, Double> output = new HashMap<>();
        list
                .getRates()
                .forEach(r -> output.put(r.getCurrency(), (r.getAsk() - r.getBid())));

        return output;
    }

    private Table getSortedTable(List<Table[]> currencies) {

        Table list = currencies.get(0)[0];

        list
                .getRates()
                .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));

        return list;
    }


}

