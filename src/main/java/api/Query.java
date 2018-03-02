package api;

import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Ore;

import java.io.IOException;
import java.util.List;

public interface Query {

    default List<?> getAllDataFrom(Date startDate, String address) throws IOException {
        return getAllDataFrom(startDate, Date.getCurrentDate(), address);
    }

    Ore getDataFrom(Date date, String address) throws InvalidArgumentException;

    List<?> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException;

}
