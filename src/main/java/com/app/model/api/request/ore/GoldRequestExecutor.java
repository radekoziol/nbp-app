package com.app.model.api.request.ore;

import com.app.model.api.request.Request;
import com.app.model.api.request.RequestExecutor;
import com.app.model.api.request.RequestParser;

public class GoldRequestExecutor extends RequestExecutor {

    public GoldRequestExecutor( Request request) {
        super(new GoldRequestParser(request), request);
    }
}
