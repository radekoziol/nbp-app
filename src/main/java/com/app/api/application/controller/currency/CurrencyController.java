package com.app.api.application.controller.currency;

import com.app.api.application.controller.ApplicationController;
import com.app.model.api.date.Date;
import com.app.model.currency.statistics.CurrencyStats;
import javafx.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController()
@RequestMapping(path = "api/currency")
public class CurrencyController extends ApplicationController {


    @RequestMapping(path = "/getCurrencyPrize")
    public @ResponseBody
    ResponseEntity<String> getCurrencyPrize(@RequestParam String currency, @RequestParam String date) {
        try {
            CurrencyStats currencyStats = new CurrencyStats();

            double price = currencyStats.getCurrencyPrice(new Date(date), currency);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(price));
        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }

    }


    /**
     * @return TODO not working (too much requests?)
     */
    @RequestMapping(path = "/getMostVolatileCurrency")
    public @ResponseBody
    ResponseEntity<String> getMostVolatileCurrency(@RequestParam String from, @RequestParam String to) {
        try {
            CurrencyStats currencyStats = new CurrencyStats();
            String currency = currencyStats.getMostVolatileCurrency(new Date(from), new Date(to));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(currency);

        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }

    }


    @RequestMapping(path = "/getMinBidPrice")
    public @ResponseBody
    ResponseEntity<String> getMinBidPrice(@RequestParam String date) {

        try {
            CurrencyStats currencyStats = new CurrencyStats();
            Pair<String, Double> currencyAndPrice = currencyStats.getMinBidPrice(new Date(date));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(currencyAndPrice.getKey() + " " + currencyAndPrice.getValue());
        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }


    }


    @RequestMapping(path = "/getDatesWhenCurrencyWasMostAndLeastExpensive")
    public @ResponseBody
    ResponseEntity<String> getDatesWhenCurrencyWasMostAndLeastExpensive(@RequestParam String currency) {
        try {
            CurrencyStats currencyStats = new CurrencyStats();
            Pair<Pair<Date, Double>, Pair<Date, Double>> mostAndLeastExpensive = currencyStats.getDatesWhenCurrencyWasMostAndLeastExpensive(currency);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mostAndLeastExpensive.getKey().getKey().toString() + " " + mostAndLeastExpensive.getKey().getValue() + " , "
                            + mostAndLeastExpensive.getValue().getKey().toString() + " " + mostAndLeastExpensive.getValue().getValue());
        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }


    }

    @RequestMapping(path = "/getSortedListOfBidPrice")
    public @ResponseBody
    ResponseEntity<String> getSortedListOfBidPrice(@RequestParam String date) {

        try {
            CurrencyStats currencyStats = new CurrencyStats();
            Map<String, Double> currencyBidPrice = currencyStats.getSortedListOfBidPrice(new Date(date));

            String output = buildOutputStringForSortedListOfBidPrice(currencyBidPrice);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(output);
        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }

    }

    private String buildOutputStringForSortedListOfBidPrice(Map<String, Double> currencyBidPrice) {

        StringBuilder stringBuilder = new StringBuilder();
        currencyBidPrice.forEach((currency, bidPrice) -> stringBuilder
                .append(currency)
                .append(" : ")
                .append(bidPrice)
                .append(" , "));


        return stringBuilder.toString();
    }


}
