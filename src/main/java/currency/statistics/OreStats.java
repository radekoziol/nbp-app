package currency.statistics;

import api.OreQuery;
import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Ore;
import currency.statistics.ListStats;

import java.io.IOException;
import java.util.List;


/**
 *
 */

public class OreStats extends ListStats{

    private static final String base = "http://api.nbp.pl/api/";

    public double getAverageGoldPrize(Date startDate, Date endDate) throws IOException {

        String address = base + "cenyzlota" + "/" ;

        OreQuery oreQuery = new OreQuery();
        List<Ore> orePrizes = oreQuery.getAllDataFrom(startDate,endDate,address);

        return super.getAverageOf(orePrizes, Ore::getCena);

    }

    public double getGoldPrize(Date date) throws IOException, InvalidArgumentException {

        OreQuery oreQuery = new OreQuery();

        return oreQuery.getDataFrom(date,base + "cenyzlota" + "/").getCena();

    }

}
