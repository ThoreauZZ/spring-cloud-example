package com.erdaoya.springcloud.comx.utils.log;

/**
 * Created by xue on 12/13/16.
 */
public interface ComxLogTrait {
    //default void log(String str){
    //}
    default ComxLogger getLogger(){
        return ComxLoggerFactory.getLogger();
    }
}
