package com.app.controller;

import com.app.model.api.date.Date;
import com.app.model.api.query.CurrencyQuery;
import com.app.model.api.query.request.Request;
import com.app.model.currency.Table;
import com.app.model.currency.statistics.CurrencyStats;
import com.app.model.currency.statistics.OreStats;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.app.model.api.date.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api")
public class ApplicationController {


    @RequestMapping(path = "/getGoldPrize")
    public @ResponseBody
    String getGoldPrize(@RequestParam String date) {
        OreStats oreStats = new OreStats();
        try {
            return String.valueOf(oreStats.getGoldPrize(new Date(date)));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return "Error occurred";
    }


    /**
     * TODO user input should be adapted
     */
    @RequestMapping(path = "/getCurrencyPrize")
    public @ResponseBody
    String getCurrencyPrize(@RequestParam String currency, @RequestParam String date) {
        try {

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
            Request request = requestBuilder
                    .setCode("exchangerates/rates/c/")
                    .setStartDate(new Date(date))
                    .setCurrency(currency)
                    .setReturnType(Table.class)
                    .build();
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Table currencyTable = (Table) currencyQuery
                    .getDataFrom(request);

            return String.valueOf(currencyTable.getRates().get(0).getAsk());

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "Error occurred";
    }


    @RequestMapping(path = "/getMostVolatileCurrency")
    public @ResponseBody
    String getMostVolatileCurrency(@RequestParam String from, @RequestParam String to) {
        try {
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();

            List<Table> rates = new LinkedList<>();

            for (Map.Entry<String, String> entry : Table.Rates.codes.entrySet()) {
                Request request = requestBuilder
                        .setCode("exchangerates/rates/c")
                        .setCurrency(entry.getKey())
                        .setStartDate(new Date(from))
                        .setEndDate(new Date(to))
                        .setReturnType(Table.class)
                        .build();

                List<Table> allRates;
                allRates = currencyQuery
                        .getAllDataFrom(request);

                rates.addAll(allRates);
            }

            CurrencyStats currencyStats = new CurrencyStats();

            return currencyStats
                    .getMostVolatileCurrency(rates, Table.Rates::getBid);

        } catch (IllegalArgumentException ex) {
            System.err.println("Given dates: " + from + "," + to +
                    " are not in right format or are invalid. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error occurred";
    }


    @RequestMapping(path = "/getAverageGoldPrize")
    public @ResponseBody
    String getAverageGoldPrize(@RequestParam String from, @RequestParam String to) {

        try {
            OreStats oreStats = new OreStats();

            return String.valueOf(oreStats.getAverageGoldPrize(new Date(from), new Date(to)));
        } catch (IllegalArgumentException ex) {
            System.err.println("Given dates: " + from + "," + to +
                    " are not in right format or are invalid. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error occurred";
    }

    @RequestMapping(path = "/getMinBidPrice")
    public @ResponseBody
    String getMinBidPrice(@RequestParam String date) {

        try {
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
            Request request = requestBuilder
                    .setCode("exchangerates/tables/c/")
                    .setStartDate(new Date(date))
                    .setReturnType(Table[].class)
                    .build();

            Table ratesArray[] = (Table[]) currencyQuery.getDataFrom(request);
            List<Table> rates = Arrays.asList(ratesArray);
            CurrencyStats currencyStats = new CurrencyStats();

            Table.Rates rate = currencyStats.getMinRateOf(rates.get(0).getRates(), Table.Rates::getBid);

            return new Pair<>
                    (String.valueOf(rate.getCurrency()), String.valueOf(rate.getBid())).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @RequestMapping(path = "/getDatesWhenCurrencyWasMostAndLeastExpensive")
    public @ResponseBody
    String getDatesWhenCurrencyWasMostAndLeastExpensive(@RequestParam String currency) {

//        http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}/
        CurrencyQuery currencyQuery = new CurrencyQuery();
        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();

        Request request = requestBuilder
                .setCode("exchangerates/rates/c")
                .setCurrency(currency)
                .setStartDate(CurrencyQuery.oldestDate)
                .setReturnType(Table[].class)
                .build();

        List<Table.Rates> allRates = new LinkedList<>();
        try {
            allRates = currencyQuery
                    .getAllDataFrom(request);

            CurrencyStats currencyStats = new CurrencyStats();
            Table.Rates min = currencyStats.getMinRateOf(allRates, Table.Rates::getBid);
            Table.Rates max = currencyStats.getMaxRateOf(allRates, Table.Rates::getBid);

            return String.valueOf(new Pair(
                    new Pair<String, String>(min.getEffectiveDate(), String.valueOf(min.getBid())),
                    new Pair<String, String>(max.getEffectiveDate(), String.valueOf(max.getBid()))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * TODO return type?
     * @return
     */
    @RequestMapping(path = "/getSortedListOfBidPrize")
    public @ResponseBody
    String getSortedListOfBidPrize(@RequestParam String date) {

        System.out.println("Difference between bid and ask for " + date + " are: \n");

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("exchangerates/tables/c")
                .setStartDate(new Date(date))
                .setReturnType(Table[].class)
                .build();


        CurrencyQuery currencyQuery = new CurrencyQuery();

        Table currencies[] = null;

        Table list = new Table();
        try {
            currencies = (Table[]) currencyQuery
                    .getDataFrom(request);
            list = currencies[0];

            assert list != null;
            list
                    .getRates()
                    .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));


            return list
                    .getRates()
                    .stream()
                    .map(r ->
                            r.getCurrency() + " - " + (r.getAsk() - r.getBid()))
                    .collect(Collectors.toList())
                    .toString();



        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }



}


