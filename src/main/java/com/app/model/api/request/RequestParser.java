package com.app.model.api.request;

import com.app.model.api.date.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class RequestParser<T> {

    protected final int MAX_DAY_NUMBER_REQUEST;
    private final RequestValidator requestValidator;
    protected final Request request;
    private List<Request> subRequests = new ArrayList<>();

    public RequestParser(int max_day_number_request, RequestValidator requestValidator, Request request) {
        MAX_DAY_NUMBER_REQUEST = max_day_number_request;
        this.requestValidator = requestValidator;
        this.request = request;
    }

    public void validateAndParseRequest(Request request) throws IOException, InterruptedException {

        requestValidator.validateRequestContent(request);

        if (canPerformOneRequest()) {
            subRequests.add(request);
        } else {
            splitRequestToMany();
        }
    }

    private void splitRequestToMany() {

        Date endDate = request.getEndDate();

        request.shiftRequestEndDate(MAX_DAY_NUMBER_REQUEST);
        while (canPerformOneRequest(request.getStartDate(), endDate)) {

            subRequests.add(request);

            request.shiftRequestEndDate(MAX_DAY_NUMBER_REQUEST);
            request.shiftRequestStartDate(MAX_DAY_NUMBER_REQUEST);
        }

        request.setEndDate(endDate);
        subRequests.add(request);
    }

    protected abstract boolean canPerformOneRequest();

    protected abstract boolean canPerformOneRequest(Date requestStartDate, Date endDate);


    public List<Request> getSubRequests() {
        return subRequests;
    }

}
