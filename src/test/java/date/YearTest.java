package date;

import date.Year;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Year class tests
 */
class YearTest {

    @Test
    void isLeapYear() {
        assertTrue(Year.isLeapYear(2012));
        assertTrue(Year.isLeapYear(2048));
        assertFalse(Year.isLeapYear(2018));
        assertTrue(Year.isLeapYear(2008));
    }


}