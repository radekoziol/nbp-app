package orePrice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import api.date.Date;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * This class is processing all gold asks
 */

public class OreParser {

    public static final String base = "http://api.nbp.pl/api/";


    public List<OrePrice> getAllDataFrom(Date startDate, Date endDate) throws IOException {

        //dla cen złota – od 2 stycznia 2013 r.
        //limit 93 dni
        if( (startDate.getYear() < 2013) || (startDate.isLaterThan(endDate))
                || (endDate.isLaterThan(Date.getCurrentDate()))) {
            System.err.println("There is problem with dates!");
            System.exit(1);
        }
        List<OrePrice> allData = new ArrayList<>();

        try {


            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            Date iterator = startDate.shiftDate(93);
            String out;

            while (endDate.isLaterThan(iterator)) {

                out = new Scanner(new URL(base + "cenyzlota" + "/" + startDate + "/" + iterator)
                        .openStream(), "UTF-8")
                        .useDelimiter("\\A").next();

                allData.addAll
                        (gson.fromJson
                                (out, new TypeToken<List<OrePrice>>() {
                                }.getType()));

                startDate = iterator;
                iterator = iterator.shiftDate(93);
            }

            out = new Scanner(new URL(base + "cenyzlota" + "/" + startDate + "/" + endDate)
                    .openStream(), "UTF-8")
                    .useDelimiter("\\A").next();

            allData.addAll
                    (gson.fromJson
                            (out, new TypeToken<List<OrePrice>>() {
                            }.getType()));
        } catch (JsonSyntaxException ex) {
            System.err.println("Something went wrong" + ex.getMessage());
        }


        return allData;

    }


    public OrePrice[] getDataFrom(Date date) throws IOException {

        //dla cen złota – od 2 stycznia 2013 r.
        if( (date.getYear() < 2013)
                || (date.isLaterThan(Date.getCurrentDate()))) {
            System.err.println("There is problem with dates!");
            System.exit(1);
        }

        try {
            String out = new Scanner(new URL(base + "cenyzlota/" + date.toString())
                    .openStream(), "UTF-8")
                    .useDelimiter("\\A").next();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();

            return gson.fromJson(out, OrePrice[].class);
        } catch (JsonSyntaxException ex) {
            System.err.println("Something went wrong" + ex.getMessage());
        }
        return null;
    }


}
