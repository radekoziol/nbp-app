package currency.statistics;

import currency.Currency;
import org.junit.jupiter.api.Test;
import currency.statistics.CurrencyStats;

import java.util.*;

import static currency.Currency.*;
import static org.junit.Assert.assertTrue;

class CurrencyStatsTest {



    // This test may work if it's feature would work :<
    /*
    @Test
    void getMinAndMaxOf() {

        List<currency.Currency> currencies = new ArrayList<>();

        //max = 1.23
        Rates rate1 = new Rates();
        rate1.setCurrency("1");
        rate1.setMid((float) 1.23);
        Rates rate2 = new Rates();
        rate2.setCurrency("1");
        rate2.setMid((float) 1.11);
        Rates rate3 = new Rates();
        rate3.setCurrency("1");
        rate3.setMid((float) 0.12);
        //max 0.1111
        Rates rate4 = new Rates();
        rate4.setCurrency("2");
        rate4.setMid((float) 0.1111);
        Rates rate5 = new Rates();
        rate5.setCurrency("2");
        rate5.setMid((float) 0.111);
        Rates rate6 = new Rates();
        rate6.setCurrency("2");
        rate6.setMid((float) 0);


        currency.Currency currency = new currency.Currency();
        currency.setRates(new Rates[]{rate1,rate4} );
        currencies.add(currency);
        currency = new currency.Currency();
        currency.setRates(new Rates[]{rate2,rate5} );
        currencies.add(currency);
        currency = new Currency();
        currency.setRates(new Rates[]{rate3,rate6} );
        currencies.add(currency);


        List<Float> test = Arrays.asList(new Float[]{(float) 1.23, (float) 0.1111});

        CurrencyStats currencyStats = new CurrencyStats();
        assertTrue( Objects.equals(test,currencyStats.getMaxRateOf(currencies,"mid")) );
    }
    */

    @Test
    void getAverageRateOf() {


        List<currency.Currency> currencies = new ArrayList<>();

        //for 1 avg is 0,82
        Rates rate1 = new Rates();
        rate1.setCurrency("1");
        rate1.setMid(1.23);
        Rates rate2 = new Rates();
        rate2.setCurrency("1");
        rate2.setMid(1.11);
        Rates rate3 = new Rates();
        rate3.setCurrency("1");
        rate3.setMid(0.12);
        //for 2 avg is 4
        Rates rate4 = new Rates();
        rate4.setCurrency("2");
        rate4.setMid(0.13);
        Rates rate5 = new Rates();
        rate5.setCurrency("2");
        rate5.setMid(11.17);
        Rates rate6 = new Rates();
        rate6.setCurrency("2");
        rate6.setMid((0.7));
        //for 3 avg is 0,13
        Rates rate7 = new Rates();
        rate7.setCurrency("3");
        rate7.setMid(0.13);
        Rates rate8 = new Rates();
        rate8.setCurrency("3");
        rate8.setMid(0.13);
        Rates rate9 = new Rates();
        rate9.setCurrency("3");
        rate9.setMid(0.13);


        currency.Currency currency = new currency.Currency();
        currency.setRates(new Rates[]{rate1,rate4,rate7} );
        currencies.add(currency);
        currency = new currency.Currency();
        currency.setRates(new Rates[]{rate2,rate5,rate8} );
        currencies.add(currency);
        currency = new currency.Currency();
        currency.setRates(new Rates[]{rate3,rate6,rate9} );
        currencies.add(currency);


        Map<String,Double> test = new HashMap<>();
        test.put("1",  0.82);
        test.put("2", (double) 4);
        test.put("3", 0.13);

        CurrencyStats currencyStats = new CurrencyStats();

        assertTrue( Objects.equals(test,currencyStats.getAverageRateOf(currencies,Rates::getMid)) );

    }


    /**
     * TODO!O!OO!O
     */
    @Test
    void getMostVolatileCurrency() {
    }


    @Test
    void getMinRateOf() {


        Rates rate1 = new Rates();
        rate1.setCurrency("3");
        rate1.setMid(1.22);
        Rates rate2 = new Rates();
        rate2.setCurrency("2");
        rate2.setMid(1.11);
        Rates rate3 = new Rates();
        rate3.setCurrency("1");
        rate3.setMid(1.22);

        List<currency.Currency> currencies = new ArrayList<>();

        currency.Currency currency = new currency.Currency();
        currency.setRates(new Rates[]{rate1,rate3,rate2} );
        currencies.add(currency);

        CurrencyStats currencyStats = new CurrencyStats();

        //Checking if currency names are equal and getMid Values also
        assertTrue(Objects.equals(
                rate2.getCurrency(),
                currencyStats.getMinRateOf(currencies,Rates::getMid).getCurrency()) );

        assertTrue( Objects.equals(
                rate2.getMid(),
                currencyStats.getMinRateOf(currencies,Rates::getMid).getMid()) );

    }

    @Test
    void getMaxRateOf() {


        //Setting up rates
        Rates rate1 = new Rates();
        rate1.setCurrency("3");
        rate1.setMid(1.23);
        Rates rate2 = new Rates();
        rate2.setCurrency("2");
        rate2.setMid(1.11);
        Rates rate3 = new Rates();
        rate3.setCurrency("1");
        rate3.setMid(0.12);

        List<currency.Currency> currencies = new ArrayList<>();

        //Creating actual currency
        currency.Currency currency = new currency.Currency();
        currency.setRates(new Rates[]{rate1,rate3,rate2} );
        currencies.add(currency);

        CurrencyStats currencyStats = new CurrencyStats();

        //Checking if currency names are equal and getMid Values also
        assertTrue(Objects.equals(
                    rate1.getCurrency(),
                    currencyStats.getMaxRateOf(currencies,Rates::getMid).getCurrency()) );

        assertTrue( Objects.equals(
                rate1.getMid(),
                    currencyStats.getMaxRateOf(currencies,Rates::getMid).getMid()) );

    }
}