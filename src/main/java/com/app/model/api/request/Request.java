package com.app.model.api.request;

import com.app.model.api.date.Date;

import java.lang.reflect.Type;

public class Request {

    /**
     * Base URL for sending requests to api
     */
    public static final String base = "http://api.nbp.pl/api/";

    protected String pageCode;
    protected Date startDate;
    protected Date endDate;
    protected Type returnType;

    protected Request(Builder<?> requestBuilder) {
        this.pageCode = requestBuilder.pageCode;
        this.startDate = requestBuilder.startDate;
        this.endDate = requestBuilder.endDate;
        this.returnType = requestBuilder.returnType;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append(base + "/").append(getPageCode()).append("/");

        if (startDate != null)
            str.append(getStartDate()).append("/");

        if (endDate != null)
            str.append(getEndDate()).append("/");

        return str.toString();
    }

    public String getPageCode() {
        return pageCode;
    }

    public Type getReturnType() {
        return returnType;
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

    public void shiftRequestStartDate(int dayNumber) {
        setStartDate(getStartDate().shiftDate(dayNumber));
    }

    public void shiftRequestEndDate(int dayNumber) {
        setEndDate(getEndDate().shiftDate(dayNumber));
    }


    public static class Builder<T extends Builder<T>> {

        private String pageCode;
        private Date startDate;
        private Date endDate;
        private Type returnType;

        public T setReturnType(Type type) {
            this.returnType = type;
            return (T) this;
        }

        public T setPageCode(String pageCode) {
            this.pageCode = pageCode;
            return (T) this;
        }

        public T setStartDate(Date startDate) {
            this.startDate = startDate;
            return (T) this;
        }

        public T setEndDate(Date endDate) {
            this.endDate = endDate;
            return (T) this;
        }

        public Request build() {
            return new Request(this);
        }

    }
}
