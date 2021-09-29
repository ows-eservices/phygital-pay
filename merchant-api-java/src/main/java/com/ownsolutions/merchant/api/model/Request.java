package com.ownsolutions.merchant.api.model;

public abstract class Request<T> {

    public Request() {
    }

    /**
     * Subclasses specify the URI path that they need to be posted to.
     *
     * @return the URI path
     */
    public abstract String getPath();

    /**
     * Subclasses specify their expected response type.
     *
     * @return the Response type
     */
    public abstract Class<T> getResponseType();
}
