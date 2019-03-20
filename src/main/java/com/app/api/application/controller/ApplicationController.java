package com.app.api.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
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


