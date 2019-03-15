package com.app.model.api.query.request;

import com.app.model.api.date.Date;
import com.app.model.currency.Table;
import javafx.util.Pair;

public class CurrencyRequest extends Request {

    private final String currency;

    public CurrencyRequest(Builder builder) {
        super(builder);
        this.currency = builder.currency;
    }

    public static Request createRequestForExchangeRatesForCurrency(Pair<Date, Date> datesFromTo, String currency) {

        return new CurrencyRequest.Builder()
                .setCurrency(currency)
                .setStartDate(datesFromTo.getKey())
                .setEndDate(datesFromTo.getValue())
                .setPageCode("exchangerates/rates/c")
                .setReturnType(Table.class)
                .build();
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append(base + "/").append(getPageCode()).append("/");

        if (getCurrencyCode() != null)
            str.append(getCurrencyCode()).append("/");

        if (getStartDate() != null)
            str.append(getStartDate()).append("/");

        if (getStartDate() != null)
            str.append(getEndDate()).append("/");

        return str.toString();
    }


    private String getCurrencyCode() {
        return Table.Rates.codes.get(getCurrency());
    }

    public String getCurrency() {
        return currency;
    }

    public static class Builder extends Request.Builder {

        private String currency;

        public Builder setRequest(CurrencyRequest request){
            setStartDate(request.startDate);
            setEndDate(request.endDate);
            setPageCode(request.pageCode);
            setReturnType(request.returnType);
            setCurrency(request.currency);
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CurrencyRequest build() {
            return new CurrencyRequest(this);
        }
    }
}
