package com.app.model.api.query;

import com.app.model.api.date.Date;
import com.app.model.api.query.request.Request;
import com.app.model.api.date.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.app.model.currency.Ore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Queries connected with ores
 * TODO should be adjusted to request class (returning types and dates)
 * TODO request.returnType makes no sense here so .. ?
 */
public class OreQuery implements Query{


    /**
     * Api has data back to 2002
     */
    private final Date oldestDate = new Date("2013-01-02");


    /**
     * Checks if date is earlier than 2013-01-02 (limited by api)
     * @param startDate
     * @param endDate
     */
    private void checkDates(Date startDate, Date endDate){

        if (!startDate.isLaterThan(oldestDate) ){
            throw new IllegalArgumentException("Date can not be earlier than 2013-01-02!");
        }
        else if (startDate.isLaterThan(Date.getCurrentDate())){
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        }
        else if (startDate.isLaterThan(endDate)){
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }

    }


    /**
     * Returns data from given date as Ore object
     * @param request
     * @return
     */
    public Ore getDataFrom(Request request) {

        checkDates(request.getStartDate(),request.getStartDate());

        try {
            String out = new Scanner(new URL( request.toString() )
                    .openStream(), "UTF-8")
                         .useDelimiter("\\A").next();

            GsonBuilder builder = new GsonBuilder();

            builder.setPrettyPrinting();
            Gson gson = builder.create();
            assert request.getReturnType().equals(Ore[].class);
            return gson.fromJson(out, Ore[].class)[0];

        } catch (JsonSyntaxException ex) {
            System.err.println(ex.getMessage() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Returns data from given period
     * @param request
     * @return
     * @throws IOException
     */
    public List<Ore> getAllDataFrom(Request request) throws IOException {

        checkDates(request.getStartDate(), request.getEndDate());

        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();

        // Returning data
        List<Ore> allData = new ArrayList<>();

        try {

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            // Since we can only send request of 93 days..
            Date iterator = startDate.shiftDate(93);
            String out;

            while (endDate.isLaterThan(iterator)) {

                out = new Scanner(new URL( request.toString() + "/" + iterator)
                        .openStream(), "UTF-8")
                        .useDelimiter("\\A").next();

                allData.addAll
                        (gson.fromJson
                                (out, new TypeToken<List<Ore>>() {
                                }.getType()));

                startDate = iterator;
                iterator = iterator.shiftDate(93);
            }

            // Rest of data
            out = new Scanner(new URL(request.toString())
                    .openStream(), "UTF-8")
                    .useDelimiter("\\A").next();

            allData.addAll
                    (gson.fromJson
                            (out, new TypeToken<List<Ore>>() {
                            }.getType()));

        } catch (JsonSyntaxException ex) {
            System.err.println(ex.getMessage());
        }


        return allData;

    }

}
