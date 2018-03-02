package currency;

import java.util.*;

public class Currency {

    //Table type
    private transient String table;
    //Table number
    private transient String no;
    //Publication api.date
    private String effectiveDate;
    private List<Rates> rates;

    public void setRates(Rates[] rates) {
        this.rates = Arrays.asList(rates);
    }

    public String getTable() {
        return table;
    }

    public String getNo() {
        return no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public List<Rates> getRates() {
        return rates;
    }


    public static class Rates {

        private transient String country;
        private transient String symbol;
        private String currency;
        //Currency code
        private transient String code;
        //
        private Double bid;
        // Exchange rate
        private Double ask;
        // Average rate
        private Double mid;


        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setMid(Double mid) {
            this.mid = mid;
        }

        public String getCountry() {
            return country;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getCurrency() {
            return currency;
        }

        public String getCode() {
            return code;
        }

        public Double getBid() {
            return bid;
        }

        public Double getAsk() {
            return ask;
        }

        public Double getMid() {
            return mid;
        }

        public void setBid(Double bid) {
            this.bid = bid;
        }

        public void setAsk(Double ask) {
            this.ask = ask;
        }
    }


}
