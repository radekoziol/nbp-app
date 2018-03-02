package currency.statistics;

import api.CurrencyQuery;
import currency.Currency;
import api.date.Date;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/*
    This class is has all analytical methods
 */
public class CurrencyStats {


    private static final String base = "http://api.nbp.pl/api/";

    /*
        Odszukuje walutę (tabela A), której kurs, począwszy od podanego dnia,
        uległ największym wahaniom (waluta, której amplituda zmian kursu jest największa)
        TODO póki co daje odpowiedz na konkretne przedziały czasu
    */

//    public Currency getMin(Currency currency) throws IOException {
//
//        CurrencyQuery currencyQuery = new CurrencyQuery();
//
//        Date startDate = new Date("2002", "1","2");
//
//        List<Currency> currencies = (List<Currency>) currencyQuery
//                .getAllDataFrom(startDate,base + "exchangerates/tables/a/");
//
//        Comparator<Currency.Rates> byMid = Comparator.comparing(Currency.Rates::getMid);
//
//        Map<Float,String> rates = new HashMap<>();
//
//        currencies.forEach(c ->
//                c.getRates()
//                        .forEach(r ->{
//                            if( (r.getCurrency() != null) && (r.getCurrency().equals(currency)) ) {
//                                rates.put(r.getMid(), c.getEffectiveDate());
//                            }
//                        }));
//
//
//        Comparator<? super Map.Entry<Float, String>> keyComparator = Comparator.comparing(Map.Entry::getKey);
//
//        return (Currency) rates.entrySet().stream().min(keyComparator).get();
//
//
//    }
//
//
//    public void getMinAndMaxOf(String currency) throws IOException {
//
//        CurrencyQuery currencyQuery = new CurrencyQuery();
//
//        Date startDate = new Date("2002", "1","2");
//
//        List<currency.Currency> currencies = (List<Currency>) currencyQuery
//                .getAllDataFrom(startDate,base + "exchangerates/tables/a/");
//
//        Comparator<Currency.Rates> byMid = Comparator.comparing(Currency.Rates::getMid);
//
//        Map<Float,String> rates = new HashMap<>();
//
//        currencies.forEach(c ->
//                c.getRates()
//                        .forEach(r ->{
//                            if( (r.getCurrency() != null) && (r.getCurrency().equals(currency)) ) {
//                                rates.put(r.getMid(), c.getEffectiveDate());
//                            }
//                        }));
//
//
//        Comparator<? super Map.Entry<Float, String>> keyComparator = Comparator.comparing(Map.Entry::getKey);
//
//        Map.Entry<Float, String> minValue = rates.entrySet()
//                .stream().min(keyComparator).get();
//
//        Map.Entry<Float, String> maxValue = rates.entrySet()
//                .stream().max(keyComparator).get();
//
//        System.out.println("For " + currency + ":");
//        System.out.println("cheapest mid price: " + minValue.getKey() + ", " + minValue.getValue());
//        System.out.println("most expensive mid price: " + maxValue.getKey() + ", " + maxValue.getValue());
//
//
//    }

    public Map<String, Double> getAverageRateOf(List<currency.Currency> currencies, Function<Currency.Rates, Double> getter) {

        Double[] averageExchangeRate = new Double[currencies.get(0).getRates().size()];
        Arrays.fill(averageExchangeRate, 0d);

        for (currency.Currency currency : currencies) {
            int counter = 0;
            for (currency.Currency.Rates rate0 : currency.getRates()) {
                averageExchangeRate[counter] += getter.apply(rate0);
                counter++;
            }
        }


        //Filling averageExchangeRate to map
        Map<String, Double> map = new HashMap<>();
        for (currency.Currency currency : currencies) {
            int counter = 0;
            for (Currency.Rates rate4 : currency.getRates()) {
                //Average is really below calculated
                averageExchangeRate[counter] /= currency.getRates().size();
                map.put(rate4.getCurrency(), averageExchangeRate[counter]);
                counter++;
            }
            break;
        }

        return map;
    }

    public List<Double> getMinRateOf(List<Currency> currencies, Function<Currency.Rates, Double> getter) {
        return getRateOf(currencies, getter, Comparator.<Double>naturalOrder().reversed());
    }

    public List<Double> getMaxRateOf(List<Currency> currencies, Function<Currency.Rates, Double> getter) {
        return getRateOf(currencies, getter, Comparator.<Double>naturalOrder());
    }

    public List<Double> getRateOf(List<Currency> currencies, Function<Currency.Rates, Double> getter, Comparator<Double> comparator) {

        Double[] maxRate = new Double
                [currencies.get(0).getRates().size()];
        Arrays.fill(maxRate, 0d);

        for (Currency currency : currencies) {
            int counter = 0;

            for (currency.Currency.Rates rate0 : currency.getRates()) {
                if (comparator
                        .compare
                                (maxRate[counter], getter.apply(rate0)) < 0) {
                    maxRate[counter] = getter.apply(rate0);

                    counter++;
                }
            }

        }
        return Arrays.asList(maxRate);



    }
}

