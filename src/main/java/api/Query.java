package api;

import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Ore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface Query {

    default List<?> getAllDataFrom(Date startDate, String address) throws IOException {
        return getAllDataFrom(startDate, Date.getCurrentDate(), address);
    }

    Object getDataFrom(Date date, String address) throws InvalidArgumentException, IOException;

    List<?> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException;

}
