package com.app.model.api.request.ore;

import com.app.model.api.request.Request;
import com.app.model.api.request.RequestExecutor;

public class GoldRequestExecutor extends RequestExecutor {

    public GoldRequestExecutor(Request request) {
        super(new GoldRequestParser(request), request);
    }
}
