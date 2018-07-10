import com.sun.javaws.exceptions.InvalidArgumentException;
import input.ProcessInput;
import org.apache.commons.cli.*;

import java.io.IOException;

/*
    TODO check the problem with dayDiffrence method (date) (negative input)
    TODO catch 400 and other responses from api -> more descriptive ex mssg
    TODO do other methods
    TODO try/catch should cover whole methods
    TODO what if for curent date response is not avivable -> try,catch
    TODO gui?
 */


public class Main {

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException, InvalidArgumentException {

        //Example input
        ProcessInput processInput = new ProcessInput(new String[]
                {
                        "-getMostVolatileCurrency", "2013-11-19", "2017-12-19"
//                        "-a" , "2015-11-19",
//                        "-getAverageGoldPrize" , "2013-11-19", "2013-12-19",
//                        "-getCurrencyPrize" , "euro", "2017-03-28",
//                        "-getMinBidPrice" , "2017-03-28"

                        });

        try {
            processInput.process();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }








}
