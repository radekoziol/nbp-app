package com.app.model.api.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class RequestExecutor<T> {

    private final RequestParser requestParser;
    private final Request request;

    public RequestExecutor(RequestParser requestParser, Request request) {
        this.request = request;
        this.requestParser = requestParser;
    }

    public <T> List<T> getAllObjectsFromRequest() throws IOException, InterruptedException {
        requestParser.validateAndParseRequest(request);
        List<Request> requests = requestParser.getSubRequests();

        return getAllDataFromRequestsAndParseToList(requests);
    }

    private <T> List<T> getAllDataFromRequestsAndParseToList(List<Request> requests) throws IOException, InterruptedException {

        Object data = getAllDataFromRequests(requests);
        if (isArray(data))
            return (List<T>) Arrays.asList(data);
        else
            return (List<T>) data;
    }

    private boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    private <T> List<T> getAllDataFromRequests(List<Request> requests) throws IOException, InterruptedException {

        List<T> data = new ArrayList<>();
        for (Request r : requests) {
            data.add(getDataFromRequest(r));
        }

        return data;
    }

    private <T> T getDataFromRequest(Request request) throws IOException, InterruptedException {
        String out = getStringOutputFromRequest(request);

        return tryParseJson(request.getReturnType(), out);
    }

    private String getStringOutputFromRequest(Request request) throws IOException {
        return new Scanner(new URL(request.toString())
                .openStream(), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }

    /**
     * For some reason nbp.api sometimes refuses to send valid response and we get some garbage instead
     */
    private <T> T tryParseJson(Type returnType, String out) throws InterruptedException {

        int counter = 0;
        while (true) {
            try {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                return gson.fromJson(out, returnType);

            } catch (IllegalStateException | JsonSyntaxException e) {
                if (counter > 10)
                    throw new InterruptedException("10 server responses in a row can not be parsed. Returning");
                counter++;
                sleep(1000);
            }
        }
    }


}
