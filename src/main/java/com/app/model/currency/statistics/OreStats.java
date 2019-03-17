package com.app.model.currency.statistics;

import com.app.model.api.date.Date;
import com.app.model.api.request.RequestExecutor;
import com.app.model.api.request.ore.GoldRequest;
import com.app.model.api.request.Request;
import com.app.model.api.request.ore.GoldRequestExecutor;
import com.app.model.api.request.ore.GoldRequestValidator;
import com.app.model.currency.Ore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is has in details ore(gold) statistical methods
 */
public class OreStats extends ListStats{

    public double getAverageGoldPrice(Date startDate, Date endDate) throws IOException, InterruptedException {

        Request request = GoldRequest.createRequestForAverageGoldPrice(startDate,endDate);

        return calculateAverageGoldPriceFromRequest(request);
    }

    private double calculateAverageGoldPriceFromRequest(Request request) throws IOException, InterruptedException {

        List<Ore> orePrices = createListOfGoldPricesFromRequest(request);

        return super.getAverageOf(orePrices, Ore::getCena);
    }

    private List<Ore> createListOfGoldPricesFromRequest(Request request) throws IOException, InterruptedException {

        GoldRequestExecutor requestExecutor = new GoldRequestExecutor(request);
        List<Ore[]> oreTablePrices = requestExecutor.getAllObjectsFromRequest();
        List<Ore> orePrices = new ArrayList<>();
        oreTablePrices
                .forEach(t -> orePrices.addAll(Arrays.asList(t)));

        return orePrices;
    }


    public double getGoldPrice(Date date) throws IOException, InterruptedException {

        Request request = GoldRequest.createRequestForGoldPrice(date);

         return getGoldPriceFromRequest(request);
    }

    private double getGoldPriceFromRequest(Request request) throws IOException, InterruptedException {

        GoldRequestExecutor requestExecutor = new GoldRequestExecutor(request);
        List<Ore[]> ores = requestExecutor.getAllObjectsFromRequest();

        return ores.get(0)[0].getCena();
    }


}
