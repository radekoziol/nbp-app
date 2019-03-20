package com.app.model.api.request.currency;

import com.app.model.api.request.Request;
import com.app.model.api.request.RequestExecutor;

public class CurrencyRequestExecutor extends RequestExecutor {

    public CurrencyRequestExecutor(Request request) {
        super(new CurrencyRequestParser(request), request);
    }
}
