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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CurrencyQuery implements Query{

    private void checkDate(Date startDate, Date endDate){

        if (startDate.getYear() < 2002){
            System.err.println("Date can not be earlier than 01-01-2002!");
            System.exit(1);
        }
        else if (startDate.isLaterThan(Date.getCurrentDate())){
            System.err.println("Start date is later than end date!");
            System.exit(1);
        }
    }

    public Currency getDataFrom(Date date, String address) throws IOException {

        checkDate(date,Date.getCurrentDate());


        String out = (new Scanner
                (new URL(address + date.toString() )
                        .openStream(), "UTF-8")
                .useDelimiter("\\A")
                .next());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        List<Currency> allData = new ArrayList<>(gson.fromJson
                (out, new TypeToken<List<Currency>>() {
                }.getType()));


        return (allData).get(0);
    }

    public List<Object> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException {

        checkDate(startDate,endDate);

        List<Currency> allData = new ArrayList<>();
        Date currentDate = Date.getCurrentDate();

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

        return Collections.singletonList(allData);

    }


}
