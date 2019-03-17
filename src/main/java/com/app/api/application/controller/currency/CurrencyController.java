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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid arguments: " + e.getMessage());
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid arguments: " + e.getMessage());
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid arguments: " + e.getMessage());
        }

    }


    @RequestMapping(path = "/getDatesWhenCurrencyWasMostAndLeastExpensive")
    public @ResponseBody
    ResponseEntity<String> getDatesWhenCurrencyWasMostAndLeastExpensive(@RequestParam String currency) {

//        http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}/
        try {

            CurrencyStats currencyStats = new CurrencyStats();
            Pair<Pair<Date, Double>, Pair<Date, Double>> mostAndLeastExpensive = currencyStats.getDatesWhenCurrencyWasMostAndLeastExpensive(currency);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mostAndLeastExpensive.getKey().getKey().toString() + " " + mostAndLeastExpensive.getKey().getValue() + " , "
                            + mostAndLeastExpensive.getValue().getKey().toString() + " " + mostAndLeastExpensive.getValue().getValue());
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid arguments: " + e.getMessage());
        }

    }
//

//    /**
//     * TODO return type?
//     *
//     * @return
//     */
//    @RequestMapping(path = "/getSortedListOfBidPrize")
//    public @ResponseBody
//    ResponseEntity<String> getSortedListOfBidPrize(@RequestParam String date) {
//
//        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
//        Request request = requestBuilder
//                .setCode("exchangerates/tables/c")
//                .setStartDate(new Date(date))
//                .setReturnType(Table[].class)
//                .build();
//
//
//        CurrencyQuery currencyQuery = new CurrencyQuery();
//
//        Table currencies[] = null;
//
//        Table list;
//        try {
//            currencies = currencyQuery
//                    .getObjectFrom(request);
//            list = currencies[0];
//
//            assert list != null;
//            list
//                    .getRates()
//                    .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));
//
//            HashMap<String, Double> output = new HashMap<>();
//
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(list
//                            .getRates()
//                            .stream()
//                            .map(r ->
//                                    r.getCurrency() + " - " + (r.getAsk() - r.getBid()) + "\n")
//                            .collect(Collectors.toList())
//                            .toString());
//
//        } catch (IOException | InterruptedException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error occurred: " + e.getMessage());
//        }
//    }


}
