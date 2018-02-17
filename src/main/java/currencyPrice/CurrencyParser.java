package currencyPrice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import date.Date;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is processing all gold asks
 */


public class CurrencyParser {

    public Currency[] getDataFrom(Date date, String address) throws IOException {

        if( (date.getYear() < 2002)
                || (date.isLaterThan(Date.getCurrentDate()))) {
            System.err.println("There is problem with dates!");
            System.exit(1);
        }

        try{
            StringBuilder out = new StringBuilder();
            out.append(new Scanner
                    (new URL(address + date.toString() + "/\n\n")
                            .openStream(), "UTF-8")
                    .useDelimiter("\\A")
                    .next());

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();


            return gson.fromJson(out.toString(), Currency[].class);
        }catch (JsonSyntaxException ex){
            System.err.println("Something went wrong" + ex.getMessage());
        }
        return null;

    }

    public List<Currency> getAllDataFrom(Date startDate, String address) throws IOException {

        if( (startDate.getYear() < 2002)
                || (startDate.isLaterThan(Date.getCurrentDate()))) {
            System.err.println("There is problem with dates!");
            System.exit(1);
        }

        List<Currency> allData = new ArrayList<>();
        Date currentDate = Date.getCurrentDate();
        currentDate = new Date("2018-01-12");

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();


            Date iterator = startDate.shiftDate(93);
            String out;

            while (currentDate.isLaterThan(iterator)) {

                out = (new Scanner
                        (new URL(address + startDate.toString() + "/" + iterator.toString() + "/\n\n")
                                .openStream(), "UTF-8")
                        .useDelimiter("\\A")
                        .next());

                allData.addAll(gson.fromJson(out, new TypeToken<List<Currency>>() {
                }.getType()));

                startDate = iterator;
                iterator = iterator.shiftDate(93);

            }

            out = (new Scanner
                    (new URL(address + startDate.toString() + "/" +
                            currentDate.shiftDate(-2).toString() + "/\n\n")
                            .openStream(), "UTF-8")
                    .useDelimiter("\\A")
                    .next());

            allData.addAll
                    (gson.fromJson
                            (out, new TypeToken<List<Currency>>() {
                            }.getType()));
        }catch (JsonSyntaxException ex){
        System.err.println("Something went wrong" + ex.getMessage());
        }

        return allData;

    }

}
