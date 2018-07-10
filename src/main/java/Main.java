import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import input.ProcessInput;
import org.apache.commons.cli.*;

import java.io.IOException;

/*
    TODO gui?
 */


public class Main {

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException, InvalidArgumentException {

        try {
            //Example input
            ProcessInput processInput = new ProcessInput();
            processInput.process(new String[]
                    {
                            "-getMostVolatileCurrency", "2013-11-19", "2017-12-19",
                            "-a" , "2015-11-19",
                            "-getAverageGoldPrize" , "2013-11-19", "2013-12-19",
                            "-getCurrencyPrize" , "euro", "2017-03-28",
                            "-getMinBidPrice" , "2017-03-28"

                    });
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }








}
