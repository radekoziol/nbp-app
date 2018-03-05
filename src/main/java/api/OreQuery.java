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

    private static final String base = "http://api.nbp.pl/api/";

    private void checkDates(Date startDate, Date endDate){

        if (startDate.getYear() < 2013){
            throw new IllegalArgumentException("Date can not be earlier than 2013-01-02!");
        }
        else if (startDate.isLaterThan(Date.getCurrentDate())){
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        }
        else if (startDate.isLaterThan(endDate)){
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }

    }

    @Override
    public Ore getDataFrom(Date date, String address) throws InvalidArgumentException {

        checkDates(date,Date.getCurrentDate());


        if(date.isLaterThan(Date.getCurrentDate())) {
            throw new InvalidArgumentException(new String[]{"Given date: " + date.toString()
                    + "is later than current one!"});
        }

        try {
            String out = new Scanner(new URL(base + address + date.toString())
                    .openStream(), "UTF-8")
                         .useDelimiter("\\A").next();

            GsonBuilder builder = new GsonBuilder();

            builder.setPrettyPrinting();
            Gson gson = builder.create();
            return gson.fromJson(out, Ore[].class)[0];

        } catch (JsonSyntaxException ex) {
            System.err.println("There is problem with received JSON!" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Ore> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException {

        checkDates(startDate,endDate);

        List<Ore> allData = new ArrayList<>();

        try {

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            Date iterator = startDate.shiftDate(93);
            String out;

            while (endDate.isLaterThan(iterator)) {

                // TODO may be changed with address
                out = new Scanner(new URL(base + address + startDate + "/" + iterator)
                        .openStream(), "UTF-8")
                        .useDelimiter("\\A").next();

                allData.addAll
                        (gson.fromJson
                                (out, new TypeToken<List<Ore>>() {
                                }.getType()));

                startDate = iterator;
                iterator = iterator.shiftDate(93);
            }

            out = new Scanner(new URL(base + address + startDate + "/" + endDate)
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
