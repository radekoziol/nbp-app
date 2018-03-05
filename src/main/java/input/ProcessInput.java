package input;

import api.CurrencyQuery;
import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sun.org.apache.xpath.internal.SourceTree;
import currency.Currency;
import currency.statistics.CurrencyStats;
import currency.statistics.OreStats;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.*;


/**
 * This class is processing (including calculating) input
 */
public class ProcessInput {

    /**
     * Program arguments
     */
    private final String[] args;

    /**
     * @param args input
     */
    public ProcessInput(String[] args) {
        this.args = args;
    }

    /**
     * This method process arguments and generate program options.
     * @throws ParseException
     * @throws IOException
     * @throws InvalidArgumentException
     */
    public void process() throws ParseException, IOException, InvalidArgumentException {

        OptionFactory optionFactory = new OptionFactory();
        Options options = optionFactory.generateOptions();

        try {
            handleOptions(options);
        }
        catch (ParseException e){
            System.err.println("There is problem with generated options or arguments! Message: ");
            System.err.println(e.getMessage());
        }


    }

    /**
     * This method handles arguments options
     * @param options
     * @throws ParseException
     * @throws IOException
     * @throws InvalidArgumentException
     */
    private void handleOptions(Options options) throws ParseException, IOException, InvalidArgumentException {

        // Following schema given on apache page
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd = parser.parse(options, args);

        if( (cmd.hasOption("help")) )  {
            formatter.printHelp(" ", options);
            System.exit(1);
            return;
        }

        if(cmd.hasOption("getGoldPrize")) {

            OreStats oreStats = new OreStats();

            System.out.println( "Gold Prize: " + oreStats.getGoldPrize
                                    (new Date(cmd.getOptionValue("getGoldPrize"))));
        }

        if(cmd.hasOption("getAverageGoldPrize")) {

            // This should be dates
            String [] dates = cmd.getOptionValues("getAverageGoldPrize");

            OreStats oreStats = new OreStats();

            try {
                System.out.println("Average gold prize from " +
                        dates[0] + " to " + dates[1] + ": " +
                        oreStats.getAverageGoldPrize(
                                new Date(dates[0]), new Date(dates[1])));
            }catch (IllegalArgumentException ex){
                System.err.println("Given dates: " + dates[0] + "," + dates[1] +
                " are not in right format or are invalid. ");
            }
        }

        if(cmd.hasOption("getMostVolatileCurrency")) {

            // This should be dates
            String [] dates = cmd.getOptionValues("getMostVolatileCurrency");

            CurrencyStats currencyStats = new CurrencyStats();

            try {
                System.out.println("Most volatile currency from " +
                        dates[0] + "to " + dates[1] + ": " +
                        currencyStats
                                .getMostVolatileCurrency(new Date(dates[0]), new Date(dates[1])));

            }catch (IllegalArgumentException ex){
                System.err.println("Given dates: " + dates[0] + "," + dates[1] +
                        " are not in right format or are invalid. ");
            }
        }

        if(cmd.hasOption("getCurrencyPrize")) {

            //First argument should be currency, second date
            String [] arguments = cmd.getOptionValues("getCurrencyPrize");

            CurrencyQuery currencyQuery = new CurrencyQuery();

            try {
                currencyQuery.getDataFrom(new Date(arguments[1]), "exchangerates/tables/a/")
                        .getRates()
                        .stream()
                        .filter(x -> x.getCurrency().equals(arguments[0]))
                        .forEach(r ->
                                System.out.println(arguments[0] + " price for " + arguments[1] + ": " + r.getMid()));
            }catch (IllegalArgumentException ex){
                System.err.println("Given arguments: " + arguments[0] + "," + arguments[1] +
                        " are not in right format or are invalid. ");
            }

        }

        if(cmd.hasOption("getMinBidPrice")) {

            String date = cmd.getOptionValue("getMinBidPrice");

            CurrencyQuery currencyQuery = new CurrencyQuery();

            try {
                List<Currency> rates = (
                        Collections.singletonList(currencyQuery.getDataFrom
                                (new Date(date), "exchangerates/tables/c/")));

                CurrencyStats currencyStats = new CurrencyStats();

                Currency.Rates rate = currencyStats.getMinRateOf(rates, Currency.Rates::getBid);

                System.out.println("For " + date + " lowest Bid Price has " + rate.getCurrency() + ": " +  rate.getBid());
            }catch (IllegalArgumentException ex){
                System.err.println("Given argument: " + date +
                        " is not in right format or is invalid. ");
            }


        }

        if(cmd.hasOption("b")) {

            System.out.println("This is not working yet");

        }

        if(cmd.hasOption("a")) {

            System.out.println("This is not working yet");

//            String date = cmd.getOptionValue("a");
//            CurrencyQuery currencyQuery = new CurrencyQuery();
//
//            List<Currency> list = Arrays.asList
//                    (currencyQuery
//                            .getDataFrom(new Date(date), "exchangerates/tables/c/"));
//
//            list.get(0)
//                    .getRates()
//                    .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));
//
//
//            list.get(0)
//                    .getRates()
//                    .forEach(r ->
//                            System.out.println(r.getCurrency() + " " + (r.getAsk() - r.getBid()) ));
        }


    }





}