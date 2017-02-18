package com.erdaoya.springcloud.comx.source;

/**
 * Created by xue on 2/9/17.
 * 业务用
 * 用于表达SourceException 且 是因为 获取的状态麻或者格式不对等原因的Exception
 */
public class SourceBizException extends SourceException{
    public SourceBizException(String message) {
        super(message);
    }
    public SourceBizException(Throwable throwable) {
        super(throwable);
    }
}
