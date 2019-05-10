package com.app.api.application.controller.currency;

import com.app.api.application.controller.ApplicationController;
import com.app.model.api.date.Date;
import com.app.model.currency.statistics.CurrencyStats;
import com.app.service.UserRequestService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController()
@RequestMapping(path = "api/currency")
public class CurrencyController extends ApplicationController {


    public CurrencyController(UserRequestService userRequestService) {
        super(userRequestService);
    }


    @RequestMapping(path = "/getCurrencyPrice")
    public @ResponseBody
    ResponseEntity<String> getCurrencyPrice(@AuthenticationPrincipal String user, @RequestParam String currency, @RequestParam String date) {
        try {
            CurrencyStats currencyStats = new CurrencyStats();

            double price = currencyStats.getCurrencyPrice(new Date(date), currency);
            userRequestService.addRequestToUser(user, "getCurrencyPrice", Arrays.asList(date, currency), Double.toString(price));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(price));
        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(user, "getCurrencyPrice", Arrays.asList(date, currency), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(user, "getCurrencyPrice", Arrays.asList(date, currency), e.getLocalizedMessage());
            return getStandardExternalErrorResponse(e);
        }

    }


    /**
     * @return TODO not working (too much requests?)
     */
    @RequestMapping(path = "/getMostVolatileCurrency")
    public @ResponseBody
    ResponseEntity<String> getMostVolatileCurrency(@AuthenticationPrincipal String user, @RequestParam String from, @RequestParam String to) {
        try {
            CurrencyStats currencyStats = new CurrencyStats();
            String currency = currencyStats.getMostVolatileCurrency(new Date(from), new Date(to));
            userRequestService.addRequestToUser(user, "getCurrencyPrice", Arrays.asList(from, to), currency);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(currency);

        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(user, "getCurrencyPrice", Arrays.asList(from, to), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(user, "getCurrencyPrice", Arrays.asList(from, to), e.getLocalizedMessage());
            return getStandardExternalErrorResponse(e);
        }

    }


    @RequestMapping(path = "/getMinBidPrice")
    public @ResponseBody
    ResponseEntity<String> getMinBidPrice(@AuthenticationPrincipal String user, @RequestParam String date) {

        try {
            CurrencyStats currencyStats = new CurrencyStats();
            Pair<String, Double> currencyAndPrice = currencyStats.getMinBidPrice(new Date(date));
            String output = currencyAndPrice.getFirst() + " " + currencyAndPrice.getSecond();
            userRequestService.addRequestToUser(user, "getMinBidPrice", Collections.singletonList(date), output);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(output);
        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(user, "getMinBidPrice", Collections.singletonList(date), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(user, "getMinBidPrice", Collections.singletonList(date), e.getLocalizedMessage());
            return getStandardExternalErrorResponse(e);
        }


    }


    @RequestMapping(path = "/getDatesWhenCurrencyWasMostAndLeastExpensive")
    public @ResponseBody
    ResponseEntity<String> getDatesWhenCurrencyWasMostAndLeastExpensive(@AuthenticationPrincipal String user, @RequestParam String currency) {
        try {
            CurrencyStats currencyStats = new CurrencyStats();
            Pair<Pair<Date, Double>, Pair<Date, Double>> mostAndLeastExpensive = currencyStats.getDatesWhenCurrencyWasMostAndLeastExpensive(currency);

            String output = buildOutputStringForDatesWhenCurrencyWasMostAndLeastExpensive(mostAndLeastExpensive);
            userRequestService.addRequestToUser(user, "getDatesWhenCurrencyWasMostAndLeastExpensive", Collections.singletonList(currency), output);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(output);
        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(user, "getDatesWhenCurrencyWasMostAndLeastExpensive", Collections.singletonList(currency), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(user, "getDatesWhenCurrencyWasMostAndLeastExpensive", Collections.singletonList(currency), e.getLocalizedMessage());
            return getStandardExternalErrorResponse(e);
        }


    }

    private String buildOutputStringForDatesWhenCurrencyWasMostAndLeastExpensive(Pair<Pair<Date, Double>, Pair<Date, Double>> mostAndLeastExpensive) {

        return mostAndLeastExpensive.getFirst().getFirst().toString() +
                " " +
                mostAndLeastExpensive.getFirst().getSecond().toString() +
                " , " +
                mostAndLeastExpensive.getSecond().getFirst().toString() +
                " " +
                mostAndLeastExpensive.getSecond().getSecond().toString();
    }

    @RequestMapping(path = "/getSortedListOfBidPrice")
    public @ResponseBody
    ResponseEntity<String> getSortedListOfBidPrice(@AuthenticationPrincipal String user, @RequestParam String date) {

        try {
            CurrencyStats currencyStats = new CurrencyStats();
            Map<String, Double> currencyBidPrice = currencyStats.getSortedListOfBidPrice(new Date(date));

            String output = buildOutputStringForSortedListOfBidPrice(currencyBidPrice);
            userRequestService.addRequestToUser(user, "getSortedListOfBidPrice", Collections.singletonList(date), output);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(output);
        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(user, "getSortedListOfBidPrice", Collections.singletonList(date), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(user, "getSortedListOfBidPrice", Collections.singletonList(date), e.getLocalizedMessage());
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
