package currencyPrice;

import date.Date;

import java.io.IOException;
import java.util.*;

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
    public String getMostVolatileCurrency(Date startDate) throws IOException,IllegalArgumentException {

        CurrencyParser currencyParser =  new CurrencyParser();
        List<Currency> currencies = currencyParser
                .getAllDataFrom(startDate,base + "exchangerates/tables/a/");

        Map<String,Float> mostVolatile = new HashMap<>();
        Currency currency = new Currency();

        //Now MaxMidRate - AverageMidRate will give as highest difference for each rate
        for (Map.Entry<String, Float> entry : getAverageRateOf(currencies,"mid").entrySet()) {
            getMaxRateOf(currencies, "mid")
                    .forEach(m ->
                            mostVolatile.put(entry.getKey(), m - entry.getValue()));
        }

        //Returning maximum
        return mostVolatile.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

    }

    public void getMinAndMaxOf(String currency) throws IOException {

        CurrencyParser currencyParser =  new CurrencyParser();
        Date startDate = new Date("2002", "1","2");
        List<Currency> currencies = currencyParser
                .getAllDataFrom(startDate,base + "exchangerates/tables/a/");

        Comparator<Currency.Rates> byMid = Comparator.comparing(Currency.Rates::getMid);

        Map<Float,String> rates = new HashMap<>();

        currencies.forEach(c ->
                c.getRates()
                        .forEach(r ->{
                            if( (r.getCurrency() != null) && (r.getCurrency().equals(currency)) ) {
                                rates.put(r.getMid(), c.getEffectiveDate());
                            }
                        }));


        Comparator<? super Map.Entry<Float, String>> keyComparator = Comparator.comparing(Map.Entry::getKey);

        Map.Entry<Float, String> minValue = rates.entrySet()
                .stream().min(keyComparator).get();

        Map.Entry<Float, String> maxValue = rates.entrySet()
                .stream().max(keyComparator).get();

        System.out.println("For " + currency + ":");
        System.out.println("cheapest mid price: " + minValue.getKey() + ", " + minValue.getValue());
        System.out.println("most expensive mid price: " + maxValue.getKey() + ", " + maxValue.getValue());


    }



    public Map<String, Float> getAverageRateOf(List<Currency> currencies, String rate) {

        Float [] averageExchangeRate = new Float[currencies.get(0).getRates().size()];
        Arrays.fill(averageExchangeRate, new Float(0));

        switch (rate.toLowerCase()) {

            case "bid": {
                //Creating averageExchangeRate values
                for(Currency currency : currencies){
                    int counter = 0;
                    for(Currency.Rates rate0 : currency.getRates()){
                        averageExchangeRate[counter] += rate0.getBid();
                        counter++;
                    }
                }

            }
            case "ask":{
                //Creating averageExchangeRate values
                for(Currency currency : currencies){
                    int counter = 0;
                    for(Currency.Rates rate1 : currency.getRates()){
                        averageExchangeRate[counter] += rate1.getBid();
                        counter++;
                    }
                }

            }
            case "mid":{
                //Creating averageExchangeRate values
                for(Currency currency : currencies){
                    int counter = 0;
                    for(Currency.Rates rate2 : currency.getRates()){
                        averageExchangeRate[counter] += rate2.getMid();
                        counter++;
                    }
                }

            }
        }


        //Filling averageExchangeRate to map
        Map<String,Float> map = new HashMap<>();
        for(Currency currency : currencies) {
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

    public List<Float> getMinRateOf(List<Currency> currencies, String rate){
        return getRateOf(currencies,rate,Comparator.<Float>naturalOrder().reversed());
    }

    public List<Float> getMaxRateOf(List<Currency> currencies, String rate){
        return getRateOf(currencies,rate,Comparator.<Float>naturalOrder());
    }

    public List<Float> getRateOf(List<Currency> currencies, String rate, Comparator<Float> comparator){

        Float[] maxRate = new Float[currencies.get(0).getRates().size()];
        Arrays.fill(maxRate, new Float(0));

        switch (rate.toLowerCase()) {
            case "mid": {
                for (Currency currency : currencies) {
                    int counter = 0;
                    for (Currency.Rates rate0 : currency.getRates()) {
                        if ( comparator.compare(maxRate[counter],rate0.getMid()) == -1 )
                            maxRate[counter] = rate0.getMid();
                        counter++;
                    }
                }
                break;
            }
            case "ask": {
                for (Currency currency : currencies) {
                    int counter = 0;
                    for (Currency.Rates rate1 : currency.getRates()) {
                        if ( comparator.compare(maxRate[counter],rate1.getAsk()) == -1  )
                            maxRate[counter] = rate1.getAsk();
                        counter++;
                    }
                }
                break;
            }
            case "bid": {
                for (Currency currency : currencies) {
                    int counter = 0;
                    for (Currency.Rates rate2 : currency.getRates()) {
                        if (comparator.compare(maxRate[counter],rate2.getBid()) == -1 )
                            maxRate[counter] = rate2.getBid();
                        counter++;
                    }
                }
            }
        }

        return Arrays.asList(maxRate);

    }





}
