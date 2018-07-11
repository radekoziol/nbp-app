package com.app.model.api.query;

import com.app.model.api.date.Date;
import com.app.model.api.query.request.Request;
import com.app.model.currency.Ore;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;

/**
 * Queries connected with ores
 * TODO should be adjusted to request class (returning types and dates)
 * TODO request.returnType makes no sense here so .. ?
 */
public class OreQuery implements Query {

    /**
     * Api has data back to 2002
     */
    private final Date oldestDate = new Date("2013-01-02");


    /**
     * Checks if date is earlier than 2013-01-02 (limited by api)
     *
     * @param startDate
     * @param endDate
     */
    public void checkDates(Date startDate, Date endDate) {

        if (!startDate.isLaterThan(oldestDate)) {
            throw new IllegalArgumentException("Date can not be earlier than 2013-01-02!");
        } else if (startDate.isLaterThan(Date.getCurrentDate())) {
            throw new IllegalArgumentException(startDate + " is later than current date " + Date.getCurrentDate());
        } else if (startDate.isLaterThan(endDate)) {
            throw new IllegalArgumentException(startDate + " is later than " + endDate);
        }

    }


}
