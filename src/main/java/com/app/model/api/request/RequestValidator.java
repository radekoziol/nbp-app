package com.app.model.api.request;

import com.app.model.api.date.Date;

public interface RequestValidator {


    default void validateRequestContent(Request request) throws IllegalArgumentException {
        if (request.getEndDate() == null) {
            checkDates(request.getStartDate(), request.getStartDate());
        } else {
            checkDates(request.getStartDate(), request.getEndDate());
        }
    }


    /**
     * This method limits dates as they are limited by api
     */
    void checkDates(Date startDate, Date endDate) throws IllegalArgumentException;

}
