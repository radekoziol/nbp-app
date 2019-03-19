package com.app.model.api.date;

/**
 * Handles useful year methods.
 */
public class Year {

    /**
     * @param year
     * @return true if given year is a leap year
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        else if (year % 100 != 0) return true;
        else return year % 400 == 0;
    }

}
