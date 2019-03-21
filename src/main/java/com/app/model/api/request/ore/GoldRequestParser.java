package com.app.model.api.request.ore;

import com.app.model.api.date.Date;
import com.app.model.api.request.Request;
import com.app.model.api.request.RequestParser;

public class GoldRequestParser extends RequestParser {

    /**
     * Limited by api
     */
    private static final int GOLD_MAX_DAY_NUMBER_REQUEST = 367;

    public GoldRequestParser(Request request) {
        super(GOLD_MAX_DAY_NUMBER_REQUEST, new GoldRequestValidator(), request);
    }

    @Override
    protected boolean canPerformOneRequest() {
        return Date.dayDifference(request.getEndDate(), request.getStartDate()) <= MAX_DAY_NUMBER_REQUEST;
    }

    @Override
    protected boolean canPerformOneRequest(Date requestStartDate, Date endDate) {
        return endDate.isLaterThan(requestStartDate);
    }

    protected void addSubRequest(Request request) {
        subRequests.add(new GoldRequest((GoldRequest) request));
    }

}
