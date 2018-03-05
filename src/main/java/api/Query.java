package api;

import api.date.Date;
import com.sun.javaws.exceptions.InvalidArgumentException;
import currency.Currency;

import java.io.IOException;
import java.util.List;

public interface Query {

    /**
     * Returns all data from given date till current date
     * @param startDate
     * @param address
     * @return
     * @throws IOException
     */
    default List<?> getAllDataFrom(Date startDate, String address) throws IOException {
        return getAllDataFrom(startDate, Date.getCurrentDate(), address);
    }


    /**
     * Returns data from date
     * @param date
     * @param address
     * @return
     * @throws InvalidArgumentException
     * @throws IOException
     */
    Object getDataFrom(Date date, String address) throws InvalidArgumentException, IOException;

    /**
     * Returns all data from given period
     * @param startDate
     * @param endDate
     * @param address
     * @return
     * @throws IOException
     */
    List<?> getAllDataFrom(Date startDate, Date endDate, String address) throws IOException;

}
