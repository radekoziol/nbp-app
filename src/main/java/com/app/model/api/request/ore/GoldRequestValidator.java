package com.app.model.api.request.ore;

import com.app.model.api.date.Date;
import com.app.model.api.request.RequestValidator;

public class GoldRequestValidator implements RequestValidator {

    /**
     * Api has data back to 2002
     */
    private final Date oldestDate = new Date("2013-01-02");


    /**
     * Checks if date is earlier than 2013-01-02 (limited by api)
     */
    @Override
    public void checkDates(Date startDate, Date endDate) throws IllegalArgumentException{
        if (!startDate.isLaterThan(oldestDate)) {
            throw new IllegalArgumentException("Date can not be earlier than " + oldestDate.toString());
        } else if (startDate.isLaterThan(Date.getCurrentDate())) {
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        } else if (startDate.isLaterThan(endDate)) {
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }
    }


}
