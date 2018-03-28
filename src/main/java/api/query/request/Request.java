package api.query.request;

import api.date.Date;

public class Request {


    /**
     * Base URL for sending requests to api
     */
    public static final String base = "http://api.nbp.pl/api/";

    private String code;
    private String table;
    private Date startDate;
    private Date endDate;

    public Request(RequestBuilder requestBuilder) {
        this.code = requestBuilder.code;
        this.table = requestBuilder.table;
        this.startDate = requestBuilder.startDate;
        this.endDate = requestBuilder.endDate;
    }


    public static class RequestBuilder{

        private String code;
        private String table;
        private Date startDate;
        private Date endDate;


        public RequestBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public RequestBuilder setTable(String table) {
            this.table = table;
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

        public Request build(){
            return new Request(this);
        }

    }


    @Override
    public String toString() {
        return base + code + startDate;
    }

    public String getCode() {
        return code;
    }

    public String getTable() {
        return table;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
