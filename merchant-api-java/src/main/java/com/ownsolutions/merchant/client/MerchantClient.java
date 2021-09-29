package com.ownsolutions.merchant.client;

import com.ownsolutions.merchant.api.model.Request;
import com.ownsolutions.merchant.api.model.Response;

public interface MerchantClient {

    /**
     * POST a Json request entity to the path specified by the concrete request.
     *
     * @param request the Request entity to be sent in the body.
     * @return a response entity extracted from the JSON http response body.
     */
    <T> T post(Request<T> request);

    /**
     * GET to the path specified.
     *
     * @param path the Get endpoint path.
     * @return a response entity extracted from the JSON http response body.
     */
    Response get(String path);

    /**
     * GET response code to the path specified.
     *
     * @param path the Get endpoint path.
     * @return a HTTP request response code.
     */
    int getResponseStatus(String path);
}

