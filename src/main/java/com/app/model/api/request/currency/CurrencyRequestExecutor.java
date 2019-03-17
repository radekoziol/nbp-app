package com.app.model.api.request.currency;

import com.app.model.api.request.Request;
import com.app.model.api.request.RequestExecutor;
import com.app.model.api.request.RequestParser;

public class CurrencyRequestExecutor extends RequestExecutor {

    public CurrencyRequestExecutor(Request request) {
        super(new CurrencyRequestParser(request), request);
    }
}
