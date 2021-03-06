package com.app.model.currency;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class describes currency
 */
public class Table implements Serializable {

    //Table type
    private transient String table;
    //Table number
    private transient String no;
    //Publication
    private String effectiveDate;
    private List<Rates> rates;
    private String currency;

    public List<Rates> getRates() {
        return rates;
    }

    public void setRates(Rates[] rates) {
        this.rates = Arrays.asList(rates);
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Describes properties of currency/rate
     */
    public static class Rates implements Serializable {

        public static HashMap<String, String> codes = generateCodes();

        private String effectiveDate;

        private transient String country;
        private transient String symbol;
        private String currency;
        // Currency code
        private String code;
        // Bid rate
        private Double bid;
        // Exchange rate
        private Double ask;
        // Average rate
        private Double mid;

        private static HashMap<String, String> generateCodes() {

            HashMap<String, String> codes = new HashMap<>();

            codes.put("forint (Węgry)", "HUF");
            codes.put("jen (Japonia)", "JPY");
            codes.put("korona czeska", "CZK");
            codes.put("korona szwedzka", "SEK");
            codes.put("korona norweska", "NOK");
            codes.put("korona duńska", "DKK");
            codes.put("dolar australijski", "AUD");
            codes.put("dolar kanadyjski", "CAD");
            codes.put("dolar amerykański", "USD");
            codes.put("frank szwajcarski", "CHF");
            codes.put("euro", "EUR");
            codes.put("SDR (MFW)", "XDR");
            codes.put("funt szterling", "GBP");

            return codes;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getBid() {
            return bid;
        }

        public void setBid(Double bid) {
            this.bid = bid;
        }

        public Double getAsk() {
            return ask;
        }

        public void setAsk(Double ask) {
            this.ask = ask;
        }

        public Double getMid() {
            return mid;
        }

        public void setMid(Double mid) {
            this.mid = mid;
        }

        public String getCode() {
            return code;
        }

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }
    }


}
