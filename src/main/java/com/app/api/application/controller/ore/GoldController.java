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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid arguments: " + e.getMessage());
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
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + "Given dates: " + from + "," + to +
                            " are not in right format or are invalid. ");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }
}
