package com.gomeplus.comx.source.sourcebase;

import com.gomeplus.comx.source.SourceException;

/**
 * Created by xue on 12/24/16.
 */
public class UnmatchedRequestMethodException extends SourceException {
    public UnmatchedRequestMethodException(String message) {
        super(message);
    }
}
