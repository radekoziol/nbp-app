package api.query;

import api.query.request.Request;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public interface Query {

    /**
     * Returns all data from given request
     *
     * @param request
     * @return
     * @throws IOException
     */
    <T> List<T> getAllDataFrom(Request request) throws IOException;


    /**
     * Returns data from date
     * @param request
     * @return
     * @throws InvalidArgumentException
     * @throws IOException
     */
    <T> T getDataFrom(Request request) throws InvalidArgumentException, IOException;

}
