package currency;

import java.util.*;

/**
 * This class describes currency
 */
public class Currency {

    //Table type
    private transient String table;
    //Table number
    private transient String no;
    //Publication
    private String effectiveDate;
    private List<Rates> rates;

    public void setRates(Rates[] rates) {
        this.rates = Arrays.asList(rates);
    }

    public List<Rates> getRates() {
        return rates;
    }

    /**
     * Describes properties of rate
     */
    public static class Rates {

        private transient String country;
        private transient String symbol;
        private String currency;
        // Currency code
        private transient String code;
        // Bid rate
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

        public String getCurrency() {
            return currency;
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
