package currencyPrice;

import org.junit.jupiter.api.Test;

import java.util.*;

import static currencyPrice.Currency.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyStatsTest {


    @Test
    void getMinAndMaxOf() {

        List<Currency> currencies = new ArrayList<>();

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


        Currency currency = new Currency();
        currency.setRates(new Rates[]{rate1,rate4} );
        currencies.add(currency);
        currency = new Currency();
        currency.setRates(new Rates[]{rate2,rate5} );
        currencies.add(currency);
        currency = new Currency();
        currency.setRates(new Rates[]{rate3,rate6} );
        currencies.add(currency);


        List<Float> test = Arrays.asList(new Float[]{(float) 1.23, (float) 0.1111});

        CurrencyStats currencyStats = new CurrencyStats();
        assertTrue( Objects.equals(test,currencyStats.getMaxRateOf(currencies,"mid")) );
    }

    @Test
    void getAverageRateOf() {


        List<Currency> currencies = new ArrayList<>();

        //for 1 avg is 0,82
        Rates rate1 = new Rates();
        rate1.setCurrency("1");
        rate1.setMid((float) 1.23);
        Rates rate2 = new Rates();
        rate2.setCurrency("1");
        rate2.setMid((float) 1.11);
        Rates rate3 = new Rates();
        rate3.setCurrency("1");
        rate3.setMid((float) 0.12);
        //for 2 avg is 0,1
        Rates rate4 = new Rates();
        rate4.setCurrency("2");
        rate4.setMid((float) 0.13);
        Rates rate5 = new Rates();
        rate5.setCurrency("2");
        rate5.setMid((float) 0.17);
        Rates rate6 = new Rates();
        rate6.setCurrency("2");
        rate6.setMid((float) 0);
        //for 3 avg is 0,13
        Rates rate7 = new Rates();
        rate7.setCurrency("3");
        rate7.setMid((float) 0.13);
        Rates rate8 = new Rates();
        rate8.setCurrency("3");
        rate8.setMid((float) 0.13);
        Rates rate9 = new Rates();
        rate9.setCurrency("3");
        rate9.setMid((float) 0.13);


        Currency currency = new Currency();
        currency.setRates(new Rates[]{rate1,rate4,rate7} );
        currencies.add(currency);
        currency = new Currency();
        currency.setRates(new Rates[]{rate2,rate5,rate8} );
        currencies.add(currency);
        currency = new Currency();
        currency.setRates(new Rates[]{rate3,rate6,rate9} );
        currencies.add(currency);


        Map<String,Float> test = new HashMap<>();
        test.put("1", (float) 0.82);
        test.put("2", (float) 0.1);
        test.put("3", (float) 0.13);

        CurrencyStats currencyStats = new CurrencyStats();
        assertTrue( Objects.equals(test,currencyStats.getAverageRateOf(currencies,"mid")) );

    }


}