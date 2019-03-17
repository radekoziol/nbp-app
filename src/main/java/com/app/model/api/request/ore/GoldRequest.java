package com.app.model.api.request.ore;

import com.app.model.api.date.Date;
import com.app.model.api.request.Request;
import com.app.model.currency.Ore;

public class GoldRequest extends Request {

    private static final String goldPageCode = "cenyzlota/";

    private GoldRequest(Builder requestBuilder) {
        super(requestBuilder);
    }

    public GoldRequest(GoldRequest request) {
        super(request);
    }

    public static GoldRequest createRequestForGoldPrice(Date date) {

        return new GoldRequest.Builder()
                .setPageCode("cenyzlota/")
                .setReturnType(Ore[].class)
                .setStartDate(date)
                .setEndDate(date)
                .build();
    }

    public static GoldRequest createRequestForAverageGoldPrice(Date startDate, Date endDate) {

        return new GoldRequest.Builder()
                .setStartDate(startDate)
                .setEndDate(startDate)
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
