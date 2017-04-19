package com.erdaoya.springcloud.comx.source;

/**
 * Created by xue on 1/12/17.
 */
public class SourceException extends Exception{

    public SourceException(String message) {
        super(message);
    }



    public SourceException(Throwable throwable) {
        super(throwable);
    }
}
