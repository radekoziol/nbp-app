package com.app.model.currency.statistics;

import com.app.model.api.query.OreQuery;
import com.app.model.api.date.Date;
import com.app.model.api.query.request.Request;
import com.sun.javaws.exceptions.InvalidArgumentException;
import com.app.model.currency.Ore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is has in details ore(gold) statistical methods
 */
public class OreStats extends ListStats{


    /**
     * Returns average gold price for given period
     * @param startDate
     * @param endDate
     * @return
     * @throws IOException
     */
    public double getAverageGoldPrice(Date startDate, Date endDate) throws IOException, InterruptedException {

        Request request = createRequestForAverageGoldPrice(startDate,endDate);

        return calculateAverageGoldPriceFromRequest(request);
    }

    private double calculateAverageGoldPriceFromRequest(Request request) throws IOException, InterruptedException {

        List<Ore> orePrices = createListOfGoldPricesFromRequest(request);

        return super.getAverageOf(orePrices, Ore::getCena);
    }

    private List<Ore> createListOfGoldPricesFromRequest(Request request) throws IOException, InterruptedException {

        OreQuery oreQuery = new OreQuery();
        List<Ore[]> oreTablePrices = oreQuery.getAllObjectsFrom(request);
        List<Ore> orePrices = new ArrayList<>();
        oreTablePrices
                .forEach(t -> orePrices.addAll(Arrays.asList(t)));

        return orePrices;
    }

    private Request createRequestForAverageGoldPrice(Date startDate, Date endDate) {

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        return requestBuilder
                .setCode("cenyzlota/")
                .setStartDate(startDate)
                .setReturnType(Ore[].class)
                .setEndDate(endDate)
                .build();
    }


    /**
     * Returns gold price for given date
     * @param date
     * @return
     */
    public double getGoldPrice(Date date) throws IOException, InterruptedException {

        Request request = createRequestForGoldPrice(date);

        return getGoldPriceFromRequest(request);
    }

    private double getGoldPriceFromRequest(Request request) throws IOException, InterruptedException {

        OreQuery oreQuery = new OreQuery();
        Ore[] ores = oreQuery.getObjectFrom(request);

        return ores[0].getCena();
    }

    private Request createRequestForGoldPrice(Date date) {

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        return requestBuilder
                .setCode("cenyzlota/")
                .setReturnType(Ore[].class)
                .setStartDate(date)
                .build();
    }

}
