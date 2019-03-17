package com.app.model.api.request.currency;

import com.app.model.api.date.Date;
import com.app.model.api.request.Request;
import com.app.model.api.request.RequestParser;
import com.app.model.api.request.RequestValidator;

public class CurrencyRequestParser extends RequestParser {

    /**
     * Limited by api
     */
    private static final int CURRENCY_MAX_DAY_NUMBER_REQUEST = 93;

    public CurrencyRequestParser( Request request) {
        super(CURRENCY_MAX_DAY_NUMBER_REQUEST, new CurrencyRequestValidator(), request);
    }

    @Override
    protected boolean canPerformOneRequest() {
        return Date.dayDifference(request.getEndDate(), request.getStartDate()) <= MAX_DAY_NUMBER_REQUEST;
    }

    @Override
    protected boolean canPerformOneRequest(Date requestStartDate, Date endDate) {
        return endDate.isLaterThan(requestStartDate);
    }

}
