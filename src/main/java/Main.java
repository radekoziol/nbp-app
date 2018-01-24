import currencyPrice.Currency;
import currencyPrice.CurrencyParser;
import currencyPrice.CurrencyStats;
import date.Date;
import input.ProcessInput;
import orePrice.GoldPrice;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.*;

/*

przy czym pojedyncze zapytanie nie może obejmować przedziału dłuższego, niż 93 dni.

TODO Date class
TODO obsłużyć wszelkie wyjątki typu: brak połączenia, złe daty
TODO jakos dobrze to zapakowac

 */




public class Main {

    private static final String base = "http://api.nbp.pl/api/";

    public static void main(String[] args) throws IOException, ParseException {

        ProcessInput processInput = new ProcessInput(args);

        processInput.process();

    }








}
