package com.app.model.api.request;

import com.app.model.api.date.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.app.model.api.request.RequestValidator.MAX_DAY_NUMBER_REQUEST;

public class RequestParser<T> {

    private final RequestValidator requestValidator;
    private final Request request;
    private List<Request> subRequests = new ArrayList<>();

    public RequestParser(RequestValidator requestValidator, Request request) {
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

    private boolean canPerformOneRequest() {
        return Date.dayDifference(request.getEndDate(), request.getStartDate()) <= MAX_DAY_NUMBER_REQUEST;
    }

    private boolean canPerformOneRequest(Date requestStartDate, Date endDate) {
        return endDate.isLaterThan(requestStartDate);
    }

    public List<Request> getSubRequests() {
        return subRequests;
    }

}
