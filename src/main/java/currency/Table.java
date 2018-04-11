package currency;

import java.util.List;

public class Table {

    private String table;
    private String country;
    private String symbol;
    private String code;
    private List<Currency.Rates> rates;


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Currency.Rates> getRates() {
        return rates;
    }

    public void setRates(List<Currency.Rates> rates) {
        this.rates = rates;
    }
}
