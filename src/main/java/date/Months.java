package date;


public enum Months {

    January("31"),
    February1("28"),
    February2("29"),
    March("31"),
    April ("30"),
    May ("31"),
    June("30"),
    July ("31"),
    August ("31"),
    September ("30"),
    October ("31"),
    November ("30"),
    December ("31");


    private String day;

    Months(String day){
        this.day = day;
    }

    public int getDay() {
        return Integer.parseInt(day);
    }
}

