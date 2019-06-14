package com.app.api.application.controller.ore;

import com.app.api.application.controller.ApplicationController;
import com.app.model.api.date.Date;
import com.app.model.currency.statistics.OreStats;
import com.app.service.UserRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@RestController()
@RequestMapping(path = "api/ore")
public class GoldController extends ApplicationController {

    public GoldController(UserRequestService userRequestService) {
        super(userRequestService);
    }

    @GetMapping(path = "/getGoldPrice")
    public @ResponseBody
    ResponseEntity<String> getGoldPrice(@AuthenticationPrincipal String username, @RequestParam String date) {
        try {
            OreStats oreStats = new OreStats();

            double price = oreStats.getGoldPrice(new Date(date));
            userRequestService.addRequestToUser(username, "getGoldPrice", Collections.singletonList(date), Double.toString(price));

            return ResponseEntity
                    .status(HttpStatus.OK).body(String.valueOf(price));
        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(username, "getGoldPrice", Collections.singletonList(date), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(username, "getGoldPrice", Collections.singletonList(date), e.getLocalizedMessage());
            return getStandardExternalErrorResponse(e);
        }

    }


    @RequestMapping(path = "/getAverageGoldPrice")
    public @ResponseBody
    ResponseEntity<String> getAverageGoldPrice(@AuthenticationPrincipal String username, @RequestParam String from, @RequestParam String to) {

        try {
            OreStats oreStats = new OreStats();
            double goldPrice = oreStats.getAverageGoldPrice(new Date(from), new Date(to));
            userRequestService.addRequestToUser(username, "getAverageGoldPrice", Arrays.asList(from, to), Double.toString(goldPrice));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(goldPrice));
        } catch (IOException | InterruptedException e) {
            userRequestService.addRequestToUser(username, "getAverageGoldPrice", Arrays.asList(from, to), e.getLocalizedMessage());
            return getStandardInternalErrorResponse(e);
        } catch (IllegalArgumentException e) {
            userRequestService.addRequestToUser(username, "getAverageGoldPrice", Arrays.asList(from, to), e.getLocalizedMessage());
            return getStandardExternalErrorResponse(e);
        }
    }
}
