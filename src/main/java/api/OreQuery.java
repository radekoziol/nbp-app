package api;

import api.date.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Ore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OreQuery implements Query{

    @Override
    public Ore getDataFrom(Date date, String address) throws InvalidArgumentException {

        if(date.isLaterThan(Date.getCurrentDate())) {
            throw new InvalidArgumentException(new String[]{"Given date: " + date.toString()
                    + "is later than current one!"});
        }

        try {
            String out = new Scanner(new URL(address + date.toString())
                    .openStream(), "UTF-8")
                         .useDelimiter("\\A").next();

            GsonBuilder builder = new GsonBuilder();

            builder.setPrettyPrinting();
            Gson gson = builder.create();
            System.out.println(out);
            System.out.println(gson.fromJson(out, Ore[].class));
            return gson.fromJson(out, Ore[].class)[0];

        } catch (JsonSyntaxException ex) {
            System.err.println("Something went wrong" + ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Ore> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException {

        //dla cen złota – od 2 stycznia 2013 r.
        //limit 93 dni
        if (!startDate.isLaterThan(new Date("2013-01-02"))){
            System.err.println("Date can not be earlier than 02-01-2013!");
            System.exit(1);
        }
        else if (startDate.isLaterThan(Date.getCurrentDate())){
            System.err.println("Start date is later than end date!");
            System.exit(1);
        }

        List<Ore> allData = new ArrayList<>();

        try {

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            Date iterator = startDate.shiftDate(93);
            String out;

            while (endDate.isLaterThan(iterator)) {

                // TODO may be changed with address
                out = new Scanner(new URL(address + startDate + "/" + iterator)
                        .openStream(), "UTF-8")
                        .useDelimiter("\\A").next();

                allData.addAll
                        (gson.fromJson
                                (out, new TypeToken<List<Ore>>() {
                                }.getType()));

                startDate = iterator;
                iterator = iterator.shiftDate(93);
            }

            out = new Scanner(new URL(address + startDate + "/" + endDate)
                    .openStream(), "UTF-8")
                    .useDelimiter("\\A").next();

            allData.addAll
                    (gson.fromJson
                            (out, new TypeToken<List<Ore>>() {
                            }.getType()));
        } catch (JsonSyntaxException ex) {
            System.err.println("Something went wrong" + ex.getMessage());
        }


        return allData;

    }
}
