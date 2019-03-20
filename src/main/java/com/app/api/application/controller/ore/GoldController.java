package com.app.api.application.controller.ore;

import com.app.api.application.controller.ApplicationController;
import com.app.model.api.date.Date;
import com.app.model.currency.statistics.OreStats;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping(path = "api/ore")
public class GoldController extends ApplicationController {


    @GetMapping(path = "/getGoldPrize")
    public @ResponseBody
    ResponseEntity<String> getGoldPrize(@RequestParam String date) {
        try {
            OreStats oreStats = new OreStats();

            double price = oreStats.getGoldPrice(new Date(date));

            return ResponseEntity
                    .status(HttpStatus.OK).body(String.valueOf(price));
        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }

    }


    @RequestMapping(path = "/getAverageGoldPrize")
    public @ResponseBody
    ResponseEntity<String> getAverageGoldPrize(@RequestParam String from, @RequestParam String to) {

        try {
            OreStats oreStats = new OreStats();
            double goldPrice = oreStats.getAverageGoldPrice(new Date(from), new Date(to));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(goldPrice));
        } catch (IOException | InterruptedException e) {
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            return getStandardExternalErrorResponse(e);
        }
    }
}
