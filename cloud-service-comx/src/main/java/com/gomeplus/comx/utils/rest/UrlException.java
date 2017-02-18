package com.gomeplus.comx.utils.rest;

import java.io.IOException;

/**
 * Created by xue on 12/24/16.
 */
public class UrlException extends Exception {
    public UrlException(String message) {
        super(message);
    }
    public UrlException(Throwable throwable) {
        super(throwable);
    }
}
