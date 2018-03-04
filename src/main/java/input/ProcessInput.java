package input;

import api.CurrencyQuery;
import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Currency;
import currency.statistics.CurrencyStats;
import currency.statistics.OreStats;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.*;


/**
 * This class is processing input and it's more a intermediary between other classes
 */
public class ProcessInput {

    public static final String base = "http://api.nbp.pl/api/";

    private final String[] args;

    public ProcessInput(String[] args) {
        this.args = args;
    }

    public void process() throws ParseException, IOException, InvalidArgumentException {

        OptionFactory optionFactory = new OptionFactory();

        Options options = optionFactory.generateOptions();

        handleOptions(options);

    }

    private void handleOptions(Options options) throws ParseException, IOException, InvalidArgumentException {

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

            String [] dates = cmd.getOptionValues("getAverageGoldPrize");

            OreStats oreStats = new OreStats();

            System.out.println( "Average gold prize from " +
                    dates[0] + " to " + dates[1] +  ": " +
                    oreStats.getAverageGoldPrize(
                            new Date(dates[0]),new Date(dates[1])));
        }

        if(cmd.hasOption("getMostVolatileCurrency")) {

            String [] dates = cmd.getOptionValues("getMostVolatileCurrency");

            CurrencyStats currencyStats = new CurrencyStats();

            System.out.println( "Most volatile currency from " +
                    dates[0] + "to " + dates[1] + ": " +
                    currencyStats
                            .getMostVolatileCurrency(new Date(dates[0]), new Date(dates[1])));

        }

        if(cmd.hasOption("getCurrencyPrize")) {

            // First argument should be currency, second date
            String [] args = cmd.getOptionValues("getCurrencyPrize");

            CurrencyQuery currencyQuery = new CurrencyQuery();

            currencyQuery.getDataFrom (new Date(args[1]), base + "exchangerates/tables/a/")
                    .getRates()
                    .stream()
                    .filter(x -> x.getCurrency().equals(args[0]))
                    .forEach(r ->
                            System.out.println(args[0] + " price for " + args[1] + ": " + r.getMid()));
        }

        if(cmd.hasOption("getMinBidPrice")) {

            String date = cmd.getOptionValue("getMinBidPrice");
            CurrencyQuery currencyQuery = new CurrencyQuery();

            List<Currency> rates = (
                    Collections.singletonList(currencyQuery.getDataFrom
                            (new Date(date), base + "exchangerates/tables/c/")));

            CurrencyStats currencyStats = new CurrencyStats();

            Currency.Rates rate = currencyStats.getMinRateOf(rates, "getBid");

            System.out.println("For " + date + " lowest Bid Price has " + rate.getCurrency() + ": " +  rate.getBid());

            }

        if(cmd.hasOption("b")) {

            System.out.println("This is not working yet");

        }

        if(cmd.hasOption("a")) {

            String date = cmd.getOptionValue("a");
            CurrencyQuery currencyQuery = new CurrencyQuery();

            List<Currency> list = Arrays.asList
                    (currencyQuery
                            .getDataFrom(new Date(date), base + "exchangerates/tables/c/"));

            list.get(0)
                    .getRates()
                    .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));


            list.get(0)
                    .getRates()
                    .forEach(r ->
                            System.out.println(r.getCurrency() + " " + (r.getAsk() - r.getBid()) ));
        }


    }





}