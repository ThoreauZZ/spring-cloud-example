package com.erdaoya.springcloud.comx.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xue on 12/13/16.
 */

/**
 *  trace debug info warning error
 *    1    2     2     2      2     dev
 *    1    2     2     2      2     test
 *                                  integrated
 *    0    1     1     1      1     pre
 *               1     1      1     pro
 *
 * 0, 不记录
 * 1, 代表记录本地日志
 * 2, 代表输出到页面debug信息
 *
 * 将debug 信息输出到页面
 * 另 可以指定参数输出所有信息  TODO
 */
public class ComxLogger {
    private static Logger logger = LoggerFactory.getLogger(ComxLogger.class);
    private String traceId = "";
    private String mode = "";
    private List<String> list = new ArrayList<>();
    public void setTraceId(String traceId) {
        this.traceId    = traceId;
    }

    /**
     * 只有debug状态才页面输出
     * @param str
     */
    public void appendDebugInfo(String str) {
        switch (mode) {
            case "pro": return;
            case "pre": return;
            default: list.add(str);
        }
    }
    public List<String> getDebugInfo() {
        return list;
    }






    // TODO 实现。。
    public void trace(String str)   { appendDebugInfo(str);logger.trace(traceId + " " + str);}
    public void debug(String str)   { appendDebugInfo(str);logger.debug(traceId + " " + str);}
    public void info(String str)    { appendDebugInfo(str);logger.info (traceId + " " + str);}
    public void warn(String str)    { appendDebugInfo(str);logger.warn (traceId + " " + str);}
    public void error(String str)   { appendDebugInfo(str);logger.error(traceId + " " + str);}

    public void trace(Throwable throwable) {
        throwable.printStackTrace();
    }
    public void debug(Throwable throwable) {
        throwable.printStackTrace();
    }
    public void info(Throwable throwable) {
        throwable.printStackTrace();
    }
    public void warn(Throwable throwable) {
        throwable.printStackTrace();
    }
    public void error(Throwable throwable) {
        throwable.printStackTrace();
    }
}
