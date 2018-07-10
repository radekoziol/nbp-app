package com.app.model.api.query;

import api.query.request.Request;
import com.app.model.api.date.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class CurrencyQuery implements Query {


    /**
     * Api has data back to 2002
     */
    static public Date oldestDate = new Date("2002-01-01");

    /**
     * Base URL for sending requests to api
     */
    private static final String base = "http://api.nbp.pl/api/";

    /**
     * Checks if date is earlier than 2002-01-01 (limited by api)
     *
     * @param startDate
     * @param endDate
     */
    private void checkDates(Date startDate, Date endDate) {

        if (oldestDate.isLaterThan(startDate)) {
            throw new IllegalArgumentException("Date " + startDate + " can not be earlier than 01-01-2002!");
        } else if (startDate.isLaterThan(Date.getCurrentDate())) {
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        } else if (startDate.isLaterThan(endDate)) {
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }

    }


    /**
     * Returns data from given request
     *
     * @param request
     * @return
     * @throws IOException
     */
    public <T> T getDataFrom(Request request) throws IOException {

        checkDates(request.getStartDate(), request.getStartDate());

        String out = (new Scanner
                (new URL(request.toString())
                        .openStream(), "UTF-8")
                .useDelimiter("\\A")
                .next());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(out, request.getReturnType());
    }


    /**
     * Returns data from given period
     *
     * @param request
     * @return
     * @throws IOException
     */
    public <T> List<T> getAllDataFrom(Request request) throws IOException {

        checkDates(request.getStartDate(), request.getStartDate());

        List<T> allData = new ArrayList<>();
        Date currentDate = Date.getCurrentDate();

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();


            Date iterator = request.getStartDate().shiftDate(93);

            Date startDate = request.getStartDate();
            String out;

            while (currentDate.isLaterThan(iterator)) {

                out = (new Scanner
                        (new URL(request.getBase() + startDate.toString() + "/" + iterator.toString() + "/\n\n")
                                .openStream(), "UTF-8")
                        .useDelimiter("\\A")
                        .next());

                allData.add(gson.fromJson(out, request.getReturnType()));

                startDate = iterator;
                iterator = iterator.shiftDate(93);

            }

            out = (new Scanner
                    (new URL(request.getBase() + startDate.toString() + "/" +
                            currentDate.shiftDate(-2).toString() + "/\n\n")
                            .openStream(), "UTF-8")
                    .useDelimiter("\\A")
                    .next());

            allData.add
                    (gson.fromJson
                            (out, request.getReturnType()));
        } catch (JsonSyntaxException ex) {
            System.err.println("Something went wrong while parsing!\n" + ex.getMessage());
        }

        return allData;

    }

}
