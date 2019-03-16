package com.app.model.api.request.currency;

import com.app.model.api.date.Date;
import com.app.model.api.request.RequestValidator;

public class CurrencyRequestValidator implements RequestValidator {

    /**
     * Api has data back to 2002
     */
    public static final Date oldestDate = new Date("2002-01-02");


    /**
     * Checks if date is earlier than 2002-01-01 (limited by api)
     */
    @Override
    public void checkDates(Date startDate, Date endDate) throws IllegalArgumentException {

        if (oldestDate.isLaterThan(startDate)) {
            throw new IllegalArgumentException("Date " + startDate + " can not be earlier than 01-01-2002!");
        } else if (startDate.isLaterThan(Date.getCurrentDate())) {
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        } else if (startDate.isLaterThan(endDate)) {
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }
    }

}
