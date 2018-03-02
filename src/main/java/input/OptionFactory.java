package input;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionFactory {

    public OptionFactory() {

    }

    public Options generateOptions() {

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
                .numberOfArgs(2)
                .argName("YYYY-MM-DD")
                .argName("YYYY-MM-DD")
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
