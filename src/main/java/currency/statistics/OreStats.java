package currency.statistics;

import api.query.OreQuery;
import api.date.Date;
import api.query.request.Request;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Ore;

import java.io.IOException;
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
    public double getAverageGoldPrize(Date startDate, Date endDate) throws IOException {

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("cenyzlota/")
                .setStartDate(startDate)
                .setEndDate(endDate)
                .build();

        OreQuery oreQuery = new OreQuery();
        List<Ore> orePrizes = oreQuery.getAllDataFrom(request);

        return super.getAverageOf(orePrizes, Ore::getCena);

    }


    /**
     * Returns gold price for given date
     * @param date
     * @return
     * @throws IOException
     */
    public double getGoldPrize(Date date) throws IOException, InvalidArgumentException {

        OreQuery oreQuery = new OreQuery();

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("cenyzlota/")
                .setStartDate(date)
                .build();

        return oreQuery.getDataFrom(request).getCena();

    }

}
