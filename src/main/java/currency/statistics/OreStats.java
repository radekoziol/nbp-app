package currency.statistics;

import api.OreQuery;
import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Ore;
import currency.statistics.ListStats;

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

        String address = "cenyzlota" + "/" ;

        OreQuery oreQuery = new OreQuery();
        List<Ore> orePrizes = oreQuery.getAllDataFrom(startDate,endDate,address);

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

        return oreQuery.getDataFrom(date,"cenyzlota" + "/").getCena();

    }

}
