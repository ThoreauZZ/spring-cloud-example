package com.erdaoya.springcloud.comx.source;

/**
 * Created by xue on 2/9/17.
 * 业务用
 * 业务来源的状态码, 传递出去
 * 用于表达SourceException 且 是因为 获取的状态麻或者格式不对等原因的Exception
 */
public class SourceBizException extends SourceException{
    int statusCode = 500;
    public SourceBizException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public SourceBizException(String message) {
        super(message);
    }
    public SourceBizException(Throwable throwable) {
        super(throwable);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
