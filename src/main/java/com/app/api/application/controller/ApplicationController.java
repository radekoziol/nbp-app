package com.app.api.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ApplicationController {

    @GetMapping("/home")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        return "home";
    }

    protected ResponseEntity<String> getStandardExternalErrorResponse(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid arguments: " + e.getMessage());
    }

    protected ResponseEntity<String> getStandardInternalErrorResponse(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred: " + e.getMessage());
    }


}


