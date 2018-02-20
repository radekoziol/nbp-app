import input.ProcessInput;
import org.apache.commons.cli.*;

import java.io.IOException;

/*

przy czym pojedyncze zapytanie nie może obejmować przedziału dłuższego, niż 93 dni.

TODO Date class
TODO obsłużyć wszelkie wyjątki typu: brak połączenia, złe daty
TODO jakos dobrze to zapakowac

 */




public class Main {

    private static final String base = "http://api.nbp.pl/api/";

    public static void main(String[] args) throws IOException, ParseException {

        ProcessInput processInput = new ProcessInput(new String[]{""});

        processInput.process();

    }








}
