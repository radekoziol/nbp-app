import com.sun.javaws.exceptions.InvalidArgumentException;
import input.ProcessInput;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

/*
    TODO gui?
 */


public class Main {

    public static void main(String[] args) {

        try {
            //Example input
            ProcessInput processInput = new ProcessInput();
            processInput.process(new String[]
                    {
                            "-a" , "2015-11-19",
                            "-getAverageGoldPrize" , "2013-11-19", "2013-12-19",
                            "-getCurrencyPrize" , "euro", "2017-03-28",
                            "-getMinBidPrice" , "2017-03-28",
                            "-getMostVolatileCurrency", "2013-11-19", "2017-12-19"
                    });
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }








}
