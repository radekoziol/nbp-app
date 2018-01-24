package date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;


/*
    This class cares for dates
 */

public class Date{

    private String year;
    private String month;
    private String day;

    public Date() {
    }

    public Date(String date){
        String [] temp = date.split("-",3);
        checkDate(temp[0],temp[1],temp[2]);
        this.year = temp[0];
        this.month = temp[1];
        this.day = temp[2];
    }

    public Date(String year, String month, String day) {
        checkDate(year,month,day);
        this.year = year;
        this.month = month;
        this.day = day;
    }


    private void checkDate(String year, String month, String day) throws IllegalArgumentException {

        if(!( (Integer.parseInt(year) > 0 ) ||
            ((Integer.parseInt(month) > 0) && (Integer.parseInt(month) <= 12)) ||
                ((Integer.parseInt(day) > 0) && (Integer.parseInt(day) <= 31)) ||
                (dayIsWithinMonth(Integer.parseInt(day),Integer.parseInt(month))) ))
            throw new IllegalArgumentException("This date is impossible!");

    }

    //TODO leap year are not considered

    public boolean dayIsWithinMonth(int day, int month) throws IllegalArgumentException{

        switch (month){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:{
                return (day >= 0) && (day <= 31);
            }
            case 4: case 6: case 9: case 11:{
                return (day >= 0) && (day <= 30);
            }
            default:
                return (day >= 0) && (day <= 29);
        }

    }


    public int getYear() {
        return Integer.parseInt(year);
    }

    public int getMonth() {
        return Integer.parseInt(month);
    }

    public int getDay() {
        return Integer.parseInt(day);
    }

    //        ISO 4217
    @Override
    public String toString() {

        String output = year + "-";

        if(month.length() == 1)
            output += "0" + month + "-";
        else output += month + "-";

        if(day.length() == 1)
            output += "0" + day;
        else output += day;

        return output;
    }

    public boolean isLaterThan(Date endDate) {
        if(getYear() - endDate.getYear() > 0)
            return true;
        return dayDifference(endDate) > 0;
    }

    //TODO  it may also work
    // TODO It may consider also length of months..
    public Integer dayDifference(Date date) {

//        2018-01-12
//        2018-04-11

        int sum = getDay() - date.getDay();

        if((getYear() - date.getYear()) >= 0 )
            if(getMonth() - date.getMonth() > 0) {
                for (int i = getMonth(); i< date.getMonth(); i++) {
                    switch (i) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            sum += 31;
                            break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            sum += 30;
                            break;

                        default:
                            sum += 29;
                    }
                }
             }
             else{
                for (int i = date.getMonth(); i > getMonth(); i--) {
                    switch (i) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            sum -= 31;
                            break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            sum -= 30;
                            break;

                        default:
                            sum -= 29;
                    }
                }
            }


        if((getYear() - date.getYear()) == 1)
            for (int i = 1; i< date.getMonth(); i++) {
                switch (i) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        sum += 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        sum += 30;
                        break;

                    default:
                        sum += 29;
                }
            }


        return sum;
    }

    public Date getCurrentDate(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String[] date = dtf.format(now).split("-",3);
        return new Date(date[0], date[1], "14");
    }

    public Date shiftDate(Integer dayNumber){

        int month = this.getMonth() + (int) dayNumber/31;

        if(12 - month >= 0)
            return new Date(year, String.valueOf(month),day );
        return new Date (String.valueOf(getYear() + 1), String.valueOf(month - 12), day);

    }



}
