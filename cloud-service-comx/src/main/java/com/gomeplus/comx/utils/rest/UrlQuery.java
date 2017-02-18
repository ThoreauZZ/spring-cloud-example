package com.gomeplus.comx.utils.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

import com.google.common.base.Splitter;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * Created by xue on 12/19/16.
 * TODO 优化 解析逻辑 暂时实现最简单逻辑
 * TODO UTF-8 bug 修正
 * http://server/action?id=a&id=b | http://server/action?id=a,b
 * multiple values: since the specification has no definition for this situation,
 * use id=a&id=b to do this;
 * and more info: for PHP, it suggests a style: id[]=a&id[]=b;
 */
public class UrlQuery implements ArrayAccessBase {
    private String queryString;
    private HashMap<String, String> parsedParameters;

    public UrlQuery(String queryString, HashMap<String, String> parsedParameters) {
        this.queryString = queryString;
        this.parsedParameters = parsedParameters;
    }

    // getters
    public HashMap<String, String> getParameters() {
        return this.parsedParameters;
    }
    public String getQueryString() {
        return this.queryString;
    }





    // implements interface
    public boolean containsKey(Object key) {
        return parsedParameters.containsKey(key);
    }

    public Object get(Object key) {
        return parsedParameters.get(key);
    }
    public Object put(Object key, Object value) {
        return parsedParameters.put((String)key, (String)value);
    }
}