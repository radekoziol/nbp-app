package input;

import api.query.CurrencyQuery;
import api.date.Date;
import api.query.request.Request;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Table;
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
     *
     * @throws ParseException
     * @throws IOException
     * @throws InvalidArgumentException
     */
    public void process() throws ParseException, IOException, InvalidArgumentException {

        OptionFactory optionFactory = new OptionFactory();
        Options options = optionFactory.generateOptions();

        try {
            handleOptions(options);
        } catch (ParseException e) {
            System.err.println("There is problem with generated options or arguments! Message: ");
            System.err.println(e.getMessage());
        }


    }


    /**
     * This method handles arguments options
     *
     * @param options
     * @throws ParseException
     * @throws IOException
     * @throws InvalidArgumentException
     */
    private void handleOptions(Options options) throws ParseException, IOException, InvalidArgumentException {

        // Following schema given on apache page
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = parser.parse(options, args);
        HelpFormatter formatter = new HelpFormatter();

        if ((cmd.hasOption("help"))) {
            formatter.printHelp(" ", options);
            System.exit(0);
        }

        Arrays.stream(cmd.getOptions())
                .forEach((Option o) -> {
                    switch (o.getOpt()) {
                        case "getGoldPrize":
                            printGoldPrize(cmd.getOptionValue("getGoldPrize"));
                            break;
                        case "getAverageGoldPrize":
                            printAverageGoldPrize(cmd.getOptionValues("getAverageGoldPrize"));
                            break;
                        case "getMostVolatileCurrency":
                            printMostVolatileCurrency(cmd.getOptionValues("getMostVolatileCurrency"));
                            break;
                        case "getCurrencyPrize":
                            printCurrencyPrice(cmd.getOptionValues("getCurrencyPrize"));
                            break;
                        case "getMinBidPrice":
                            printMinBidPrice(cmd.getOptionValue("getMinBidPrice"));
                            break;
                        case "a":
                            printA(cmd.getOptionValue("a"));
                            break;
                        case "b":
                            printB(cmd.getOptionValue("b"));
                            break;
                    }
                });


    }


    /**
     * Prints gold price for given date
     *
     * @param date
     */
    private void printGoldPrize(String date) {

        OreStats oreStats = new OreStats();

        try {
            System.out.println("Gold Prize: " + oreStats.getGoldPrize
                    (new Date(date)));
        } catch (IOException | InvalidArgumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints average gold prize for given dates
     *
     * @param dates
     */
    private void printAverageGoldPrize(String[] dates) {

        OreStats oreStats = new OreStats();

        try {
            System.out.println("Average gold prize from " +
                    dates[0] + " to " + dates[1] + ": " +
                    oreStats.getAverageGoldPrize(
                            new Date(dates[0]), new Date(dates[1])));
        } catch (IllegalArgumentException ex) {
            System.err.println("Given dates: " + dates[0] + "," + dates[1] +
                    " are not in right format or are invalid. ");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints most volatile currency for given dates
     *
     * @param dates
     */
    private void printMostVolatileCurrency(String[] dates) {

        try {
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();

            List<Table> rates = new LinkedList<>();

            for (Map.Entry<String, String> entry : Table.Rates.codes.entrySet()) {
                Request request = requestBuilder
                        .setCode("exchangerates/rates/c")
                        .setCurrency(entry.getKey())
                        .setStartDate(new Date(dates[0]))
                        .setEndDate(new Date(dates[1]))
                        .setReturnType(Table.class)
                        .build();

                List<Table> allRates;
                allRates = currencyQuery
                        .getAllDataFrom(request);

                rates.addAll(allRates);
            }
            CurrencyStats currencyStats = new CurrencyStats();
            System.out.println("Most volatile(bid price) currency from " +
                    dates[0] + " to " + dates[1] + ": " +
                    currencyStats
                            .getMostVolatileCurrency(rates, Table.Rates::getBid));

        } catch (IllegalArgumentException ex) {
            System.err.println("Given dates: " + dates[0] + "," + dates[1] +
                    " are not in right format or are invalid. ");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints currency price for given date
     *
     * @param arguments
     */
    private void printCurrencyPrice(String[] arguments) {

        try {

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
            Request request = requestBuilder
                    .setCode("exchangerates/rates/c/")
                    .setStartDate(new Date(arguments[1]))
                    .setCurrency(arguments[0])
                    .setReturnType(Table.class)
                    .build();
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Table currency = (Table) currencyQuery
                    .getDataFrom(request);

            System.out.println(arguments[0] + " price for "
                    + arguments[1] + ": bid = " + currency.getRates().get(0).getBid()
                    + " ask = " + currency.getRates().get(0).getAsk());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }


    }

    private void printB(String currency) {
        System.out.println("Currency " + currency +
                " was the cheapest on: ");

//        http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}/
        CurrencyQuery currencyQuery = new CurrencyQuery();
        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();

        Request request = requestBuilder
                .setCode("exchangerates/rates/c")
                .setCurrency(currency)
                .setStartDate(CurrencyQuery.oldestDate)
                .setReturnType(Table[].class)
                .build();

        List<Table.Rates> allRates = new LinkedList<>();
        try {
            allRates = currencyQuery
                    .getAllDataFrom(request);
        } catch (IOException e) {
            e.printStackTrace();
        }


        CurrencyStats currencyStats = new CurrencyStats();
        Table.Rates min = currencyStats.getMinRateOf(allRates, Table.Rates::getBid);
        Table.Rates max = currencyStats.getMaxRateOf(allRates, Table.Rates::getBid);


        System.out.println(currency + " was the cheapest on " +
                min.getEffectiveDate() + " - " +
                min.getBid());

        System.out.println(currency + " was the most expensive on " +
                max.getEffectiveDate() + " - " +
                max.getBid());


    }

    private void printA(String date) {

        System.out.println("Difference between bid and ask for " + date + " are: \n");

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        Request request = requestBuilder
                .setCode("exchangerates/tables/c")
                .setStartDate(new Date(date))
                .setReturnType(Table[].class)
                .build();


        CurrencyQuery currencyQuery = new CurrencyQuery();

        Table currencies[] = null;

        Table list = new Table();
        try {
            currencies = (Table[]) currencyQuery
                    .getDataFrom(request);
            list = currencies[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert list != null;
        list
                .getRates()
                .sort(Comparator.comparing(e -> e.getAsk() - e.getBid()));


        list
                .getRates()
                .forEach(r ->
                        System.out.println(r.getCurrency() + " - " + (r.getAsk() - r.getBid())));

    }


    private void printMinBidPrice(String date) {

        try {
            CurrencyQuery currencyQuery = new CurrencyQuery();

            Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
            Request request = requestBuilder
                    .setCode("exchangerates/tables/c/")
                    .setStartDate(new Date(date))
                    .setReturnType(Table[].class)
                    .build();

            Table ratesArray[] = (Table[]) currencyQuery.getDataFrom(request);
            List<Table> rates = Arrays.asList(ratesArray);
            CurrencyStats currencyStats = new CurrencyStats();

            Table.Rates rate = currencyStats.getMinRateOf(rates.get(0).getRates(), Table.Rates::getBid);

            System.out.println("For " + date + " lowest Bid Price has " + rate.getCurrency() + ": " + rate.getBid());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}