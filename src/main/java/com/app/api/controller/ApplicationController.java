package com.app.api.controller;

import com.app.exceptions.NoSuchNoteException;
import com.app.model.api.date.Date;
import com.app.model.api.query.CurrencyQuery;
import com.app.model.api.query.request.Request;
import com.app.model.currency.Table;
import com.app.model.currency.statistics.CurrencyStats;
import com.app.model.currency.statistics.OreStats;
import com.app.repository.NoteRepository;
import com.app.service.NoteService;
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


    @RequestMapping(path = "/getGoldPrize", method = RequestMethod.GET)
    public @ResponseBody
    double getGoldPrize(@RequestParam Date date) {
        OreStats oreStats = new OreStats();
        try {
            return oreStats.getGoldPrize(date);
        } catch (IOException | InvalidArgumentException e) {
            e.printStackTrace();
        }
        return -1;

    }


    /**
     * TODO user input should be adapted
     */
    @RequestMapping(path = "/getCurrencyPrize", method = RequestMethod.GET)
    public @ResponseBody
    double getCurrencyPrize(@RequestParam String currency, @RequestParam String date) {
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

            return currencyTable.getRates().get(0).getAsk();

        } catch (IOException | IllegalArgumentException | InvalidArgumentException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @RequestMapping(path = "/getMostVolatileCurrency", method = RequestMethod.GET)
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
        return "Error";
    }


    @RequestMapping(path = "/getAverageGoldPrize", method = RequestMethod.GET)
    public @ResponseBody
    double getAverageGoldPrize(@RequestParam String from, @RequestParam String to) {

        try {
            OreStats oreStats = new OreStats();

            return oreStats.getAverageGoldPrize(new Date(from), new Date(to));
        } catch (IllegalArgumentException ex) {
            System.err.println("Given dates: " + from + "," + to +
                    " are not in right format or are invalid. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @RequestMapping(path = "/getMinBidPrice", method = RequestMethod.GET)
    public @ResponseBody
    Pair<String,Double> getMinBidPrice(@RequestParam String date) {

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

            return new Pair<>(rate.getCurrency(), rate.getBid());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }



    @RequestMapping(path = "/getDatesWhenCurrencyWasMostAndLeastExpensive", method = RequestMethod.GET)
    public @ResponseBody
    Pair<Pair<String,Double>,Pair<String,Double>> getDatesWhenCurrencyWasMostAndLeastExpensive(@RequestParam String currency) {

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

            return new Pair<>(
                    new Pair<String, Double>(min.getEffectiveDate(), min.getBid()),
                    new Pair<String, Double>(max.getEffectiveDate(), max.getBid()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * TODO return type?
     * @return
     */
    @RequestMapping(path = "/getSortedListOfBidPrize", method = RequestMethod.GET)
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



        } catch (IOException | InvalidArgumentException e) {
            e.printStackTrace();
        }


        return null;

    }



}


