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
    public double getAverageGoldPrize(Date startDate, Date endDate) throws IOException, InterruptedException {

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("cenyzlota/")
                .setStartDate(startDate)
                .setReturnType(Ore[].class)
                .setEndDate(endDate)
                .build();

        OreQuery oreQuery = new OreQuery();
        List<Ore[]> oreTablePrizes = oreQuery.getAllObjectsFrom(request);
        List<Ore> orePrizes = new ArrayList<>();
        oreTablePrizes
                .forEach(t -> orePrizes.addAll(Arrays.asList(t)));

        return super.getAverageOf(orePrizes, Ore::getCena);
    }


    /**
     * Returns gold price for given date
     * @param date
     * @return
     */
    public double getGoldPrize(Date date) throws InvalidArgumentException, IOException, InterruptedException {

        OreQuery oreQuery = new OreQuery();

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("cenyzlota/")
                .setReturnType(Ore[].class)
                .setStartDate(date)
                .build();

        Ore[] ores = oreQuery.getObjectFrom(request);
        return ores[0].getCena();
    }

}
