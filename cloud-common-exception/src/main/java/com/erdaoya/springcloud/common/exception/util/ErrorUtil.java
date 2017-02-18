package com.erdaoya.springcloud.common.exception.util;

import com.erdaoya.springcloud.common.exception.entity.FullError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by shangshengfang on 2017/2/16.
 */
@Slf4j
public class ErrorUtil {
    public static String appendError(String message, Error error) {
        FullError wrap=new FullError();
        wrap.setMessage(message);
        wrap.setError(error);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = message;
        try {
            jsonString = mapper.writeValueAsString(wrap);
        }catch(Exception e){
            log.error("exceptionWithErrorObjectTranslateFail,{}",e);
        }
        return jsonString;
    }
}
