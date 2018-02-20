package input;

import currencyPrice.Currency;
import currencyPrice.CurrencyParser;
import statistics.CurrencyStats;
import api.date.Date;
import orePrice.GoldPrice;
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

    public void process() throws ParseException, IOException {

        org.apache.commons.cli.Options options = generateOptions();

        handleOptions(options);

    }

    private void handleOptions(Options options) throws ParseException, IOException {

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        }catch (ParseException exp){
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }

        assert cmd != null;
        if( (cmd.hasOption("help")) )  {
            formatter.printHelp(" ", options);
            System.exit(1);
            return;
        }

        if(cmd.hasOption("getGoldPrize")) {
                    GoldPrice goldPrice = new GoldPrice();
                    System.out.println( "Gold Prize: " +
                            goldPrice.getGoldPrize
                                    (new Date(cmd.getOptionValue("getGoldPrize"))));
        }

        if(cmd.hasOption("getAverageGoldPrize")) {

            String [] dates = cmd.getOptionValue("getAverageGoldPrize").split(":",2);

            GoldPrice goldPrice = new GoldPrice();
            System.out.println( "Average gold prize from " + dates[0]
                    + " to " + dates[1] +  ": " +
                    goldPrice.getAverageGoldPrize(
                            new Date(dates[0]),new Date(dates[1])));
        }

        if(cmd.hasOption("getMostVolatileCurrency")) {

            String date = cmd.getOptionValue("getMostVolatileCurrency");

            CurrencyStats currencyStats = new CurrencyStats();
                    System.out.println( "Most volatile currency from " +  date + ": " +
                            currencyStats.getMostVolatileCurrency(
                                    new Date(date)));

        }

        if(cmd.hasOption("getCurrencyPrize")) {

            String [] curr;

            curr = cmd.getOptionValue("getCurrencyPrize").split("_", 3);
            String currency = curr[0] + " " + curr[1];


            CurrencyParser currencyParser = new CurrencyParser();

            System.out.println( currency + " price for: " + curr[2]);
            Arrays.asList(currencyParser.getDataFrom
                                    (new Date(curr[2]), base + "exchangerates/tables/a/"))
                    .get(0)
                    .getRates()
                    .stream()
                    .filter(x -> x.getCurrency().equals(currency))
                    .forEach( r -> System.out.println(r.getMid()));
        }

        if(cmd.hasOption("getMinBidPrice")) {

            String date = cmd.getOptionValue("getMinBidPrice");
            CurrencyParser currencyParser = new CurrencyParser();

            List <Currency.Rates> temp = (
                            Arrays.asList
                                    (currencyParser.getDataFrom
                                            (new Date(date), base + "exchangerates/tables/c/"))
                                    .get(0)
                                    .getRates()
                    );
            Currency.Rates rate = temp.stream()
                    .min(Comparator.comparing(Currency.Rates::getBid))
                    .orElseThrow(NoSuchElementException::new);

            System.out.println("For " + date + " lowest Bid Price has " + rate.getCurrency() + ": " +  rate.getBid());

            }

        if(cmd.hasOption("b")) {

            String [] curr;
            String currency;
            if(cmd.getOptionValue("b").contains("_")) {
                curr = cmd.getOptionValue("b").split("_", 2);
                currency = curr[0] + " " + curr[1];
            }
            else
                currency = cmd.getOptionValue("b");

            CurrencyStats currencyStats = new CurrencyStats();
            currencyStats.getMinAndMaxOf(currency);

        }

        if(cmd.hasOption("a")) {

            String date = cmd.getOptionValue("a");
            CurrencyParser currencyParser = new CurrencyParser();

            List<Currency> list = Arrays.asList
                    (currencyParser
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


    private Options generateOptions() {

        Options options = new Options();

        options.addOption(Option.builder("help")
                .desc("prints this")
                .build());

        options.addOption(Option.builder("getGoldPrize")
                .hasArgs()
                .argName("YYYY-MM-DD")
                .desc("returns gold prize")
                .build());

        options.addOption(Option.builder("getCurrencyPrize")
                .hasArgs()
                .argName("currency_YYYY-MM-DD")
                .desc("returns currency prize - if currency contains 2 words they must be divided with '_' ")
                .build());

        options.addOption(Option.builder("getMostVolatileCurrency")
                .desc("returns most volatile currency")
                .hasArgs()
                .numberOfArgs(1)
                .argName("YYYY-MM-DD")
                .build());

        options.addOption(Option.builder("getAverageGoldPrize")
                .hasArgs()
                .numberOfArgs(1)
                .argName("YYYY-MM-DD:YYYY-MM-DD")
                .desc("returns average gold prize from a period")
                .build());

        options.addOption(Option.builder("getMinBidPrice")
                .hasArgs()
                .argName("YYYY-MM-DD")
                .numberOfArgs(1)
                .desc("returns minimum bid prize for certain day")
                .build());

        options.addOption(Option.builder("b")
                .hasArgs()
                .numberOfArgs(1)
                .argName("currency")
                .desc("for a currency return 2 api.date when currency was " +
                        "the cheapest and the most expensive - if currency contains 2 words they must be divded with '_'  ")
                .build());

        options.addOption(Option.builder("a")
                .hasArgs()
                .numberOfArgs(1)
                .argName("YYYY-MM-DD")
                .desc("returns sorted (ascending) difference between bid and ask prize for certain day")
                .build());


        return options;

    }


}