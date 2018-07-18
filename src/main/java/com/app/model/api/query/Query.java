package com.app.model.api.query;

import com.app.model.api.date.Date;
import com.app.model.api.query.request.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 *
 */
public interface Query {


    /**
     * This method limits dates as they are limited by api
     *
     * @param startDate
     * @param endDate
     */
    void checkDates(Date startDate, Date endDate);

    /**
     * Returns all data from given dates from request
     *
     * @param request
     * @return
     * @throws IOException
     */
    default <T> List<T> getAllObjectsFrom(Request request) throws IOException, InterruptedException {

        checkDates(request.getStartDate(), request.getEndDate());

        Date endDate = request.getEndDate();

        // Returning data
        List<T> allData = new ArrayList<>();

        // Since we can only send request of 93 days..
        request.setEndDate(request.getStartDate().shiftDate(93));
        String out;

        while (endDate.isLaterThan(request.getEndDate())) {

            out = new Scanner(new URL(request.toString())
                    .openStream(), "UTF-8")
                    .useDelimiter("\\A").next();

            allData.add(tryParseJson(request.getReturnType(), out));

            request.setStartDate(request.getEndDate());
            request.setEndDate(request.getEndDate().shiftDate(93));
        }

        // Rest of data
        out = new Scanner(new URL(request.toString())
                .openStream(), "UTF-8")
                .useDelimiter("\\A").next();

        allData.add(tryParseJson(request.getReturnType(), out));

        return allData;
    }

    /**
     * Returns data from given date from request
     *
     * @param request
     * @return
     * @throws IOException
     */
    default <T> T getObjectFrom(Request request) throws IOException, InterruptedException {

        checkDates(request.getStartDate(), request.getStartDate());

        String out = (new Scanner
                (new URL(request.toString())
                        .openStream(),
                        "UTF-8")
                .useDelimiter("\\A")
                .next());

        return tryParseJson(request.getReturnType(), out);
    }

    /**
     * For some reason nbp.api sometimes refuses to send valid response and we get some html instead
     *
     * @param <T>
     * @param returnType
     * @param out
     * @return
     * @throws InterruptedException
     */
    default <T> T tryParseJson(Type returnType, String out) throws InterruptedException {

        int counter = 0;
        while (true) {
            try {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                T ret = gson.fromJson(out, returnType);
                return ret;

            } catch (IllegalStateException | JsonSyntaxException e) {
                if (counter > 100)
                    throw new InterruptedException("10 server responses in a row can not be parsed. Returning");
                counter++;
                sleep(1000);
            }
        }
    }
}
