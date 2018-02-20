package api;

import currencyPrice.CurrencyParser;
import api.date.Date;

import java.util.List;

public class Query {

    public List<Object> getAllDatafrom(Date startDate){

        CurrencyParser currencyParser =  new CurrencyParser();
        String base;
        List<currencyPrice.Currency> currencies = currencyParser
                .getAllDataFrom(startDate,base + "exchangerates/tables/a/");
    }





}
