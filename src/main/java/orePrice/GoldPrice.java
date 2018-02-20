package orePrice;

import com.google.gson.JsonSyntaxException;
import api.date.Date;

import java.io.IOException;
import java.util.List;


/**
 *
 */

public class GoldPrice extends OrePrice{

    public float getAverageGoldPrize(Date startDate, Date endDate) throws IOException {

        OreParser oreParser = new OreParser();

        List<OrePrice> orePrizes = oreParser.getAllDataFrom(startDate,endDate);

        return (float) orePrizes.stream()
                .mapToDouble(s -> s.cena)
                .summaryStatistics()
                .getAverage();

    }

    public float getGoldPrize(Date date) throws IOException {

        OreParser oreParser = new OreParser();
        OrePrice[] orePrize = null;
        try {
            orePrize = oreParser.getDataFrom(date);
        }catch (JsonSyntaxException ex){
            System.err.println("Something went wrong!");
        }

        return orePrize[0].cena;

    }

}
