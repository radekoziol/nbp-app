package currency.statistics;

import currency.Table;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Though I'm not sure if more abstract methods should be tested (ListStats class)
 */
class CurrencyStatsTest {


    @Test
    void getAverageRateOf() {


        List<currency.Table> currencies = new ArrayList<>();

        //for 1 avg is 0,82
        Table.Rates rate1 = new Table.Rates();
        rate1.setCurrency("1");
        rate1.setMid(1.23);
        Table.Rates rate2 = new Table.Rates();
        rate2.setCurrency("1");
        rate2.setMid(1.11);
        Table.Rates rate3 = new Table.Rates();
        rate3.setCurrency("1");
        rate3.setMid(0.12);
        //for 2 avg is 4
        Table.Rates rate4 = new Table.Rates();
        rate4.setCurrency("2");
        rate4.setMid(0.13);
        Table.Rates rate5 = new Table.Rates();
        rate5.setCurrency("2");
        rate5.setMid(11.17);
        Table.Rates rate6 = new Table.Rates();
        rate6.setCurrency("2");
        rate6.setMid((0.7));
        //for 3 avg is 0,13
        Table.Rates rate7 = new Table.Rates();
        rate7.setCurrency("3");
        rate7.setMid(0.13);
        Table.Rates rate8 = new Table.Rates();
        rate8.setCurrency("3");
        rate8.setMid(0.13);
        Table.Rates rate9 = new Table.Rates();
        rate9.setCurrency("3");
        rate9.setMid(0.13);


        currency.Table currency = new currency.Table();
        currency.setRates(new Table.Rates[]{rate1,rate4,rate7} );
        currencies.add(currency);
        currency = new currency.Table();
        currency.setRates(new Table.Rates[]{rate2,rate5,rate8} );
        currencies.add(currency);
        currency = new currency.Table();
        currency.setRates(new Table.Rates[]{rate3,rate6,rate9} );
        currencies.add(currency);


        Map<String,Double> test = new HashMap<>();
        test.put("1",  0.82);
        test.put("2", (double) 4);
        test.put("3", 0.13);

        CurrencyStats currencyStats = new CurrencyStats();

        assertTrue( Objects.equals(test,currencyStats.getAverageRateOf(currencies, Table.Rates::getMid)) );

    }


    /**
     * TODO More test here?
     */
    @Test
    void getMostVolatileCurrency() {

        //euro Table
        Table euro = new Table();

        Table.Rates rate1 = new Table.Rates();
        rate1.setBid(1.0);
        Table.Rates rate2 = new Table.Rates();
        rate2.setBid(3.0);
        Table.Rates rate3 = new Table.Rates();
        rate3.setBid(20.0);
        Table.Rates rate4 = new Table.Rates();
        rate4.setBid(1.0);
        Table.Rates rate5 = new Table.Rates();
        rate5.setBid(20.0);

        Table.Rates[] rates = new Table.Rates[]{rate1,rate2,rate3,rate4,rate5};

        euro.setRates(rates);
        euro.setCurrency("euro");

        //rand  currency Table
        Table rand = new Table();

        rate1 = new Table.Rates();
        rate1.setBid(10.0);
        rate2 = new Table.Rates();
        rate2.setBid(3.0);
        rate3 = new Table.Rates();
        rate3.setBid(4.0);
        rate4 = new Table.Rates();
        rate4.setBid(1.0);
        rate5 = new Table.Rates();
        rate5.setBid(5.0);

        rates = new Table.Rates[]{rate1, rate2, rate3, rate4, rate5};

        rand.setRates(rates);
        rand.setCurrency("rand");


        CurrencyStats currencyStats = new CurrencyStats();

        List<Table> tables = new LinkedList<>();

        tables.add(euro);
        tables.add(rand);

        assertTrue( Objects.equals("euro",currencyStats.getMostVolatileCurrency(tables, Table.Rates::getBid)) );

    }


    @Test
    void getMinRateOf() {


        Table.Rates rate1 = new Table.Rates();
        rate1.setCurrency("3");
        rate1.setMid(1.22);
        Table.Rates rate2 = new Table.Rates();
        rate2.setCurrency("2");
        rate2.setMid(1.11);
        Table.Rates rate3 = new Table.Rates();
        rate3.setCurrency("1");
        rate3.setMid(1.22);

        List<currency.Table> currencies = new ArrayList<>();

        currency.Table currency = new currency.Table();
        currency.setRates(new Table.Rates[]{rate1,rate3,rate2} );
        currencies.add(currency);

        CurrencyStats currencyStats = new CurrencyStats();

        //Checking if currency names are equal and getMid Values also
        assertTrue(Objects.equals(
                rate2.getCurrency(),
                currencyStats.getMinRateOf(currencies.get(0).getRates(), Table.Rates::getMid).getCurrency()) );

        assertTrue( Objects.equals(
                rate2.getMid(),
                currencyStats.getMinRateOf(currencies.get(0).getRates(), Table.Rates::getMid).getMid()) );

    }

    @Test
    void getMaxRateOf() {


        //Setting up rates
        Table.Rates rate1 = new Table.Rates();
        rate1.setCurrency("3");
        rate1.setMid(1.23);
        Table.Rates rate2 = new Table.Rates();
        rate2.setCurrency("2");
        rate2.setMid(1.11);
        Table.Rates rate3 = new Table.Rates();
        rate3.setCurrency("1");
        rate3.setMid(0.12);

        List<currency.Table> currencies = new ArrayList<>();

        //Creating actual currency
        currency.Table currency = new currency.Table();
        currency.setRates(new Table.Rates[]{rate1,rate3,rate2} );
        currencies.add(currency);

        CurrencyStats currencyStats = new CurrencyStats();

        //Checking if currency names are equal and getMid Values also
        assertTrue(Objects.equals(
                    rate1.getCurrency(),
                    currencyStats.getMaxRateOf(currencies.get(0).getRates(), Table.Rates::getMid).getCurrency()) );

        assertTrue( Objects.equals(
                rate1.getMid(),
                    currencyStats.getMaxRateOf(currencies.get(0).getRates(), Table.Rates::getMid).getMid()) );

    }
}