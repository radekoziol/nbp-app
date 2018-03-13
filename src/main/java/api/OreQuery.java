package api;

import api.date.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Currency;
import currency.Ore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Queries connected with ores
 */
public class OreQuery implements Query{

    /**
     * Base URL for sending requests to api
     */
    private static final String base = "http://api.nbp.pl/api/";


    /**
     * Checks if date is earlier than 2013-01-02 (limited by api)
     * @param startDate
     * @param endDate
     */
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


    /**
     * Returns data from given date as Ore object
     * @param date
     * @param address
     * @return
     * @throws InvalidArgumentException
     */
    public Ore getDataFrom(Date date, String address) throws InvalidArgumentException {

        checkDates(date,Date.getCurrentDate());

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

    /**
     * Returns data from given period
     * @param startDate
     * @param endDate
     * @param address
     * @return
     * @throws IOException
     */
    public List<Ore> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException {

        checkDates(startDate,endDate);

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

            // Rest of data
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
