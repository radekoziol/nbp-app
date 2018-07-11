package api.query.request;

import api.date.Date;
import currency.Table;

import java.lang.reflect.Type;

public class Request {


    /**
     * Base URL for sending requests to api
     */
    public static final String base = "http://api.nbp.pl/api/";

    private String code;
    private Date startDate;
    private Date endDate;
    private String currency;
    private Type returnType;



    public Request(RequestBuilder requestBuilder) {
        this.code = requestBuilder.code;
        this.currency = requestBuilder.currency;
        this.startDate = requestBuilder.startDate;
        this.endDate = requestBuilder.endDate;
        this.returnType = requestBuilder.returnType;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public String getBase() {
        String str = (base + "/") + code + "/";
        if(!currency.isEmpty())
            str += currency + "/";
        return str;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        // Base
        str.append(base + "/" + code + "/");

        if (currency != null)
            str.append(currency + "/");

        if (startDate != null)
            str.append(startDate + "/");

        if (endDate != null)
            str.append(endDate + "/");


        return str.toString();
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static class RequestBuilder {

        private String code;
        private Date startDate;
        private Date endDate;
        private String currency;
        private Type returnType;


        public RequestBuilder setReturnType(Type type) {
            this.returnType = type;
            return this;
        }

        public RequestBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public RequestBuilder setCurrency(String currency) {
            this.currency = Table.Rates.codes.get(currency);
            return this;
        }

        public RequestBuilder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public RequestBuilder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }
}
