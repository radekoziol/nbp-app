package com.app.api.application.controller;

import com.app.model.api.date.Date;
import com.app.model.api.query.currency.CurrencyQuery;
import com.app.model.api.request.Request;
import com.app.model.currency.Table;
import com.app.model.currency.statistics.CurrencyStats;
import com.app.model.currency.statistics.OreStats;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class ApplicationController {


    @GetMapping("/test")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }


    @GetMapping(path = "/getGoldPrize")
    public @ResponseBody
    ResponseEntity<String> getGoldPrize(@RequestParam String date) {
        OreStats oreStats = new OreStats();
        try {
            try {
                return ResponseEntity
                        .status(HttpStatus.OK).body(String.valueOf(oreStats.getGoldPrice(new Date(date))));
            } catch (IOException | InterruptedException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error occurred: " + e.getMessage());
            }
        } catch (InvalidArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getRealMessage());
        }
    }

    /**
     * TODO user input should be adapted
     */
    @RequestMapping(path = "/getCurrencyPrize")
    public @ResponseBody
    ResponseEntity<String> getCurrencyPrize(@RequestParam String currency, @RequestParam String date) {
        try {

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
            Request request = requestBuilder
                    .setCode("exchangerates/rates/c/")
                    .setStartDate(new Date(date))
                    .setCurrency(currency)
                    .setReturnType(Table.class)
                    .build();
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Table currencyTable = currencyQuery
                    .getObjectFrom(request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(String.valueOf(currencyTable.getRates().get(0).getAsk())));
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }


    /**
     * @param from
     * @param to
     * @return TODO not working (too much requests?)
     */
    @RequestMapping(path = "/getMostVolatileCurrency")
    public @ResponseBody
    ResponseEntity<String> getMostVolatileCurrency(@RequestParam String from, @RequestParam String to) {
        try {
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();


            CurrencyStats currencyStats = new CurrencyStats();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(currencyStats
                            .getMostVolatileCurrency(new Date(from), new Date(to)));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + "Given dates: " + from + "," + to +
                            " are not in right format or are invalid. ");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }


    @RequestMapping(path = "/getAverageGoldPrize")
    public @ResponseBody
    ResponseEntity<String> getAverageGoldPrize(@RequestParam String from, @RequestParam String to) {

        try {
            OreStats oreStats = new OreStats();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(oreStats.getAverageGoldPrice(new Date(from), new Date(to))));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + "Given dates: " + from + "," + to +
                            " are not in right format or are invalid. ");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(path = "/getMinBidPrice")
    public @ResponseBody
    ResponseEntity<String> getMinBidPrice(@RequestParam String date) {

        try {
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
            Request request = requestBuilder
                    .setCode("exchangerates/tables/c/")
                    .setStartDate(new Date(date))
                    .setReturnType(Table[].class)
                    .build();

            Table ratesArray[] = currencyQuery.getObjectFrom(request);
            List<Table> rates = Arrays.asList(ratesArray);
            CurrencyStats currencyStats = new CurrencyStats();

            Table.Rates rate = currencyStats.getMinRateOf(rates.get(0).getRates(), Table.Rates::getBid);

            return ResponseEntity
                    .status(HttpStatus.OK).body(new Pair<>
                            (String.valueOf(rate.getCurrency()), String.valueOf(rate.getBid())).toString());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }

    }


    /**
     * @param currency
     * @return TODO not working lol
     */
    @RequestMapping(path = "/getDatesWhenCurrencyWasMostAndLeastExpensive")
    public @ResponseBody
    ResponseEntity<String> getDatesWhenCurrencyWasMostAndLeastExpensive(@RequestParam String currency) {

//        http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}/
        CurrencyQuery currencyQuery = new CurrencyQuery();
        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();

        Request request = requestBuilder
                .setCode("exchangerates/rates/c")
                .setCurrency(currency)
                .setStartDate(CurrencyQuery.oldestDate)
                .setEndDate(Date.getCurrentDate())
                .setReturnType(Table.class)
                .build();

        try {
            List<Table> allTables = currencyQuery
                    .getAllObjectsFrom(request);

            List<Table.Rates> allRates = new ArrayList<>();
            allTables
                    .forEach(t -> allRates.addAll(t.getRates()));


            CurrencyStats currencyStats = new CurrencyStats();
            Table.Rates min = currencyStats.getMinRateOf(allRates, Table.Rates::getBid);
            Table.Rates max = currencyStats.getMaxRateOf(allRates, Table.Rates::getBid);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(
                            new Pair<>(min.getEffectiveDate(), String.valueOf(min.getBid())).toString() + "\n " +
                                    new Pair<>(max.getEffectiveDate(), String.valueOf(max.getBid())).toString()) +"\n");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }

    }


    /**
     * TODO return type?
     *
     * @return
     */
    @RequestMapping(path = "/getSortedListOfBidPrize")
    public @ResponseBody
    ResponseEntity<String> getSortedListOfBidPrize(@RequestParam String date) {

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("exchangerates/tables/c")
                .setStartDate(new Date(date))
                .setReturnType(Table[].class)
                .build();


        CurrencyQuery currencyQuery = new CurrencyQuery();

        Table currencies[] = null;

        Table list;
        try {
            currencies = currencyQuery
                    .getObjectFrom(request);
            list = currencies[0];

            assert list != null;
            list
                    .getRates()
                    .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));

            HashMap<String, Double> output = new HashMap<>();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(list
                            .getRates()
                            .stream()
                            .map(r ->
                                    r.getCurrency() + " - " + (r.getAsk() - r.getBid()) + "\n")
                            .collect(Collectors.toList())
                            .toString());

        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }


}


