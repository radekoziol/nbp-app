package input;

import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.statistics.OreStats;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;


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

        System.out.println(Arrays.toString(cmd.getOptions()));

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

//        if(cmd.hasOption("getMostVolatileCurrency")) {
//
//            String date = cmd.getOptionValue("getMostVolatileCurrency");
//
//            CurrencyStats currencyStats = new CurrencyStats();
//                    System.out.println( "Most volatile currency from " +  date + ": " +
//                            currencyStats.getMostVolatileCurrency(
//                                    new Date(date)));
//
//        }
//
//        if(cmd.hasOption("getCurrencyPrize")) {
//
//            String [] curr;
//
//            curr = cmd.getOptionValue("getCurrencyPrize").split("_", 3);
//            String currency = curr[0] + " " + curr[1];
//
//
//            CurrencyParser currencyParser = new CurrencyParser();
//
//            System.out.println( currency + " price for: " + curr[2]);
//            Arrays.asList(currencyParser.getDataFrom
//                                    (new Date(curr[2]), base + "exchangerates/tables/a/"))
//                    .get(0)
//                    .getRates()
//                    .stream()
//                    .filter(x -> x.getCurrency().equals(currency))
//                    .forEach( r -> System.out.println(r.getMid()));
//        }
//
//        if(cmd.hasOption("getMinBidPrice")) {
//
//            String date = cmd.getOptionValue("getMinBidPrice");
//            CurrencyParser currencyParser = new CurrencyParser();
//
//            List <Currency.Rates> temp = (
//                            Arrays.asList
//                                    (currencyParser.getDataFrom
//                                            (new Date(date), base + "exchangerates/tables/c/"))
//                                    .get(0)
//                                    .getRates()
//                    );
//            Currency.Rates rate = temp.stream()
//                    .min(Comparator.comparing(Currency.Rates::getBid))
//                    .orElseThrow(NoSuchElementException::new);
//
//            System.out.println("For " + date + " lowest Bid Price has " + rate.getCurrency() + ": " +  rate.getBid());
//
//            }
//
//        if(cmd.hasOption("b")) {
//
//            String [] curr;
//            String currency;
//            if(cmd.getOptionValue("b").contains("_")) {
//                curr = cmd.getOptionValue("b").split("_", 2);
//                currency = curr[0] + " " + curr[1];
//            }
//            else
//                currency = cmd.getOptionValue("b");
//
//            CurrencyStats currencyStats = new CurrencyStats();
//            currencyStats.getMinAndMaxOf(currency);
//
//        }
//
//        if(cmd.hasOption("a")) {
//
//            String date = cmd.getOptionValue("a");
//            CurrencyParser currencyParser = new CurrencyParser();
//
//            List<Currency> list = Arrays.asList
//                    (currencyParser
//                            .getDataFrom(new Date(date), base + "exchangerates/tables/c/"));
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
//        }


    }





}