package api.query;

import api.date.Date;
import api.query.request.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Currency;
import currency.Table;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class CurrencyQuery{


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
     * @param startDate
     * @param endDate
     */
    private void checkDates(Date startDate, Date endDate){

        if (oldestDate.isLaterThan(startDate)){
            throw new IllegalArgumentException("Date " + startDate + " can not be earlier than 01-01-2002!");
        }
        else if (startDate.isLaterThan(Date.getCurrentDate())){
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        }
        else if (startDate.isLaterThan(endDate)){
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }

    }


    /**
     * Returns data from given data as Currency
     * @param request
     * @return
     * @throws IOException
     */
    public Object getCurrencyDataFrom(Request request) throws IOException{

        checkDates(request.getStartDate(),request.getStartDate());

        String out = (new Scanner
                (new URL(request.toString())
                        .openStream(), "UTF-8")
                .useDelimiter("\\A")
                .next());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(out,request.getReturnType());
    }

        /**
     * Returns data from given data as Currency
     * @param request
     * @return
     * @throws IOException
     */
    public Object getCurrencyTableFrom(Request request) throws IOException {
        checkDates(request.getStartDate(),request.getStartDate());

        String out = (new Scanner
                (new URL(request.toString())
                        .openStream(), "UTF-8")
                .useDelimiter("\\A")
                .next());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return  (gson.fromJson
                (out, request.getReturnType()));

    }


//    /**
//     * Returns data from given address
//     * @param address
//     * @return
//     * @throws IOException
//     */
//    public Currency getDataFrom(String address) throws IOException {
//
//        String out = (new Scanner
//                (new URL(base + address )
//                        .openStream(), "UTF-8")
//                .useDelimiter("\\A")
//                .next());
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        Gson gson = builder.create();
//
//        return gson.fromJson(out, Currency.class);
//    }
//
//    /**
//     * Returns all data from given period as list of list of currencies
//     * @param startDate
//     * @param endDate
//     * @param address
//     * @return
//     * @throws IOException
//     */
//    public List<Currency> getAllDataFrom(Request request) throws IOException {
//
//        checkDates(startDate,endDate);
//
//        List<Currency> allData = new ArrayList<>();
//        Date currentDate = Date.getCurrentDate();
//
//        try {
//            GsonBuilder builder = new GsonBuilder();
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//
//
//            Date iterator = startDate.shiftDate(93);
//            String out;
//
//            while (currentDate.isLaterThan(iterator)) {
//
//                out = (new Scanner
//                        (new URL(base + address + startDate.toString() + "/" + iterator.toString() + "/\n\n")
//                                .openStream(), "UTF-8")
//                        .useDelimiter("\\A")
//                        .next());
//
//                allData.addAll(gson.fromJson(out, new TypeToken<List<Currency>>() {
//                }.getType()));
//
//                startDate = iterator;
//                iterator = iterator.shiftDate(93);
//
//            }
//
//            out = (new Scanner
//                    (new URL(base + address + startDate.toString() + "/" +
//                            currentDate.shiftDate(-2).toString() + "/\n\n")
//                            .openStream(), "UTF-8")
//                    .useDelimiter("\\A")
//                    .next());
//
//            allData.addAll
//                    (gson.fromJson
//                            (out, new TypeToken<List<Currency>>() {
//                            }.getType()));
//        }catch (JsonSyntaxException ex){
//            System.err.println("Something went wrong" + ex.getMessage());
//        }
//
//        return allData;
//
//    }
//

}
