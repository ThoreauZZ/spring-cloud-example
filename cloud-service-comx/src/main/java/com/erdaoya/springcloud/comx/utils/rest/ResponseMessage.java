package com.erdaoya.springcloud.comx.utils.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by xue on 12/16/16.
 */
public class ResponseMessage {
    private Object    data;
    private Object    error;
    private JSONArray debug;

    private String  message;
    private Integer code;
    private String  jsonp;

    static final String FIELD_DEBUG                 = "debug";
    static final String FIELD_MESSAGE               = "message";
    static final String FIELD_DATA                  = "data";
    static final String FIELD_ERROR                 = "error";
    static final String DEFAULT_HEADER_CONTENT_TYPE = "Content-Type: application/json; charset=UTF-8";
    static final String JSONP_HEADER_CONTENT_TYPE   = "Content-Type: application/javascript; charset=UTF-8";
    static final String HEADER_PROTOCOL_LINE        = "HTTP/1.1";






    // constructors;
    public ResponseMessage(){}
    public ResponseMessage(Object data, String message, Integer code, Object error) {
        this.data       = data;
        this.message    = message;
        this.code       = code;
        this.error      = error;
    }
    public ResponseMessage(Object data, String message, Integer code) {
        this.data       = data;
        this.message    = message;
        this.code       = code;
    }


    public String send() {
        JSONObject body = new JSONObject();
        body.put(this.FIELD_DEBUG, this.debug);
        body.put(this.FIELD_DATA, this.data);
        body.put(this.FIELD_MESSAGE, this.message);
        return JSONObject.toJSONString(body, SerializerFeature.WriteMapNullValue);
    }

    public String sendForSpringCloud() {
        return JSONObject.toJSONString(data);
    }




















    // setter and getter
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public JSONArray getDebug() {
        return debug;
    }

    public void setDebug(JSONArray debug) {
        this.debug = debug;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getJsonp() {
        return jsonp;
    }

    public void setJsonp(String jsonp) {
        this.jsonp = jsonp;
    }
}
