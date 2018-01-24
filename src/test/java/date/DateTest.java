package date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {


    @Test
    void dayIsWithinMonth(){

        assertFalse(new Date().dayIsWithinMonth(112,1));
        assertTrue(new Date().dayIsWithinMonth(1,1));
        assertTrue(new Date().dayIsWithinMonth(31,12));
        assertTrue(new Date().dayIsWithinMonth(30,6));
        assertTrue(new Date().dayIsWithinMonth(29,2));

    }


    @Test
    void isLaterThan() {

        assertTrue(new Date
                ("1998","2","1")
                .isLaterThan(new Date
                        ("1995", "2","1")));

        assertFalse(new Date
                ("1998","2","1")
                .isLaterThan(new Date
                        ("1998", "2","1")));

        assertFalse(new Date
                ("1998","12","31")
                .isLaterThan(new Date
                        ("1999", "1","1")));
    }

    @Test
    void dayDifference() {

        assertTrue( 1 == new Date("1997","12","31").dayDifference(new Date("1998","1","1")));
        assertTrue( 12 == new Date("1997","12","1").dayDifference(new Date("1998","1","13")));
        assertTrue( 93 == new Date("1997","12","31").dayDifference(new Date("1998","3","1")));
        assertTrue( 62 == new Date("1997","12","1").dayDifference(new Date("1998","2","1")));
    }


    @Test
    void shiftDate() {

        assertTrue(new Date("1998", "2", "1").equals(new Date("1997","12","1").shiftDate(62)));
        assertTrue(new Date("1998", "2", "1").equals(new Date("1999","2","1").shiftDate(365)));
        assertTrue(new Date("1998", "9", "1").equals(new Date("1998","10","2").shiftDate(31)));


    }
}