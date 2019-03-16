package com.app.model.api.request;

import com.app.model.api.date.Date;

public interface RequestValidator {

    /**
     * Limited by nbp.api - max interval of which one may ask is 93 days
     */
    int MAX_DAY_NUMBER_REQUEST = 93;


    default void validateRequestContent(Request request) throws IllegalArgumentException{
        checkDates(request.getStartDate(), request.getEndDate());
    }


    /**
     * This method limits dates as they are limited by api
     */
    void checkDates(Date startDate, Date endDate) throws IllegalArgumentException;

}
