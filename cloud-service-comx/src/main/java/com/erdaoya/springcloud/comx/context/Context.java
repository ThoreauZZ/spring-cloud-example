package com.erdaoya.springcloud.comx.context;

import com.erdaoya.springcloud.comx.schema.Schema;
import com.erdaoya.springcloud.comx.utils.log.ComxLogger;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


/**
 * Created by xue on 12/16/16.
 * TODO localCache 处理成特殊的类
 */
public class Context {
    private Schema schema;
    private RequestMessage          request;
    private User                    user;
    private ResponseMessage         response;
    private ComxLogger logger;
    private ContextCache            cache;
    private HashMap<String, Object> localCache = new HashMap<>();
    private Boolean                 localCacheEnabled = false;

    private String          traceId;

    // TODO
    // 记录各资源请求
    // 似乎可以是个结构体来记录
    // 或者redis count http count ...?
    private Integer         count = 0;
    // 先不处理
    //private ScriptLoader scriptLoader;

    // TODO 重构
    RestTemplate restTemplate;





    public Context copy() {
        Context newContext    = new Context();
        newContext.logger     = this.getLogger();
        newContext.cache      = this.getCache();
        newContext.count      = this.getCount();
        newContext.request    = this.getRequest();
        newContext.schema     = this.getSchema();
        newContext.response   = this.getResponse();
        newContext.localCache = this.getLocalCache();
        return newContext;
    }



    // getter and setter
    public Boolean getLocalCacheEnabled() {
        return localCacheEnabled;
    }

    public void setLocalCacheEnabled(Boolean localCacheEnabled) {
        this.localCacheEnabled = localCacheEnabled;
    }

    public HashMap<String, Object> getLocalCache() {
        return localCache;
    }

    public void setLocalCache(HashMap<String, Object> localCache) {
        this.localCache = localCache;
    }

    public ContextCache getCache() {
        return cache;
    }

    public void setCache(ContextCache cache) {
        this.cache = cache;
    }

    public ResponseMessage getResponse() {
        return response;
    }

    public void setResponse(ResponseMessage response) {
        this.response = response;
    }

    public ComxLogger getLogger() {
        return logger;
    }

    public void setLogger(ComxLogger logger) {
        this.logger = logger;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public RequestMessage getRequest() {
        return request;
    }

    public void setRequest(RequestMessage request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
