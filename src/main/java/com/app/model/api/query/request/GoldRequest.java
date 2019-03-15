package com.app.model.api.query.request;

import com.app.model.api.date.Date;
import com.app.model.currency.Ore;

public class GoldRequest extends Request {

    private static final String goldPageCode = "cenyzlota/";

    public GoldRequest(Builder requestBuilder) {
        super(requestBuilder);
    }

    public static GoldRequest createRequestForGoldPrice(Date date) {

        return new GoldRequest.Builder()
                .setPageCode("cenyzlota/")
                .setReturnType(Ore[].class)
                .setStartDate(date)
                .build();
    }

    public static GoldRequest createRequestForAverageGoldPrice(Date startDate, Date endDate) {

        return new GoldRequest.Builder()
                .setStartDate(startDate)
                .setReturnType(Ore[].class)
                .setEndDate(endDate)
                .build();
    }

    public static class Builder extends Request.Builder<Builder> {

        public Builder() {
            setPageCode(goldPageCode);
        }

        public GoldRequest build() {
            return new GoldRequest(this);
        }
    }

}
