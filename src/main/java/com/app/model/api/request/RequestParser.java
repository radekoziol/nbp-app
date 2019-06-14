package com.app.model.api.request;

import com.app.model.api.date.Date;

import java.util.ArrayList;
import java.util.List;

public abstract class RequestParser<T> {

    protected final int MAX_DAY_NUMBER_REQUEST;
    protected final Request request;
    private final RequestValidator requestValidator;
    protected List<Request> subRequests = new ArrayList<>();

    protected RequestParser(int max_day_number_request, RequestValidator requestValidator, Request request) {
        MAX_DAY_NUMBER_REQUEST = max_day_number_request;
        this.requestValidator = requestValidator;
        this.request = request;
    }

    public void validateAndParseRequest(Request request) throws IllegalArgumentException {

        requestValidator.validateRequestContent(request);

        if (canPerformOneRequest()) {
            subRequests.add(request);
        } else {
            splitRequestToMany();
        }
    }

    private void splitRequestToMany() {

        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();

        request.setEndDate(startDate);
        request.shiftRequestEndDate(MAX_DAY_NUMBER_REQUEST);

        while (canPerformOneRequest(request.getEndDate(), endDate)) {

            addSubRequest(request);

            startDate = request.getStartDate();
            request.shiftRequestEndDate(MAX_DAY_NUMBER_REQUEST);
            request.shiftRequestStartDate(MAX_DAY_NUMBER_REQUEST);
        }

        request.setStartDate(startDate);
        request.setEndDate(endDate);
        addSubRequest(request);
    }

    protected abstract void addSubRequest(Request request);

    protected abstract boolean canPerformOneRequest();

    protected abstract boolean canPerformOneRequest(Date requestStartDate, Date endDate);


    public List<Request> getSubRequests() {
        return subRequests;
    }

}
