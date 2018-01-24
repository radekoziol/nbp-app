package currencyPrice;

import java.util.*;

public class Currency {

    //Table type
    private transient String table;
    //Table number
    private transient String no;
    //Publication date
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


    public static class Rates{

        private transient String country;
        private transient String symbol;
        private String currency;
        //Currency code
        private transient String code;
        //
        private Float bid;
        // Exchange rate
        private Float ask;
        // Average rate
        private Float mid;


        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setMid(Float mid) {
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

        public Float getBid() {
            return bid;
        }

        public Float getAsk() {
            return ask;
        }

        public Float getMid() {
            return mid;
        }

        public void setBid(Float bid) {
            this.bid = bid;
        }

        public void setAsk(Float ask) {
            this.ask = ask;
        }
    }


}
