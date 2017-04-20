package com.erdaoya.springcloud.comx.utils.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.SourceBizException;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.rest.clients.ApacheHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created by xue on 12/15/16.
 */
public class RequestMessage implements ArrayAccessBase{
    public static final String METHOD_GET      = "get";
    public static final String METHOD_POST     = "post";
    public static final String METHOD_DELETE   = "delete";
    public static final String METHOD_PUT      = "put";
    private static final String HEADER_FIELD_TRACE_ID           = "X-Gomeplus-Trace-Id";
    private static final String HEADER_FIELD_TRACE_ID_LOWERCASE = "x-gomeplus-trace-id";
    private static final String HEADER_FIELD_X_FORWARDED_FOR    = "X-Forwarded-For";
    private static final String QUERY_FIELD_TRACE_ID            = "traceId";
    private static final String DEFAULT_TRACE_ID_PREFIX         = "COMX";
    public static final Integer DEFAULT_TIMEOUT     = 10;

    @Deprecated
    protected HashMap<String, String>   commonParameterHolder;

    protected Url                       url;
    protected String                    method;
    protected Map<String, Object>       data;
    protected HashMap<String, String>   headerParameters;
    protected Integer                   timeout;


    //TODO 重构
    protected RestTemplate              restTemplate;
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }







    // constructors;
    public RequestMessage(Url url, String method, Map<String, Object> data, HashMap<String, String> headerParameters, Integer timeout) {
        this.url                = url;
        this.method             = method;
        this.data               = data;
        this.headerParameters   = headerParameters;
        this.timeout            = timeout;
    }



    // implements interface
    public boolean containsKey(Object key) {
        List<String> templist = Arrays.asList("url", "method", "data", "headerParameters", "timeout");
        return templist.contains(key);
    }
    public Object get(Object key) {
        if (key.equals("url"))                  return url;
        if (key.equals("method"))               return method;
        if (key.equals("data"))                 return data;
        if (key.equals("headerParameters"))     return headerParameters;
        return null;
    }








    /**
     * 传入context 是为了日共日志功能
     * 类原则上可替换
     * 抛出异常还是由业务检查抛出异常？
     * @param context 当前context
     * @return ResponseMessage
     */
    public ResponseMessage execute(Context context) throws SourceException {
        String targetUrl   = url.getUrl();
        String requestData = (null != data)?new JSONObject(data).toJSONString():"";

        // 执行 HttpClient request 函数
        // TODO 需要将错误码和信息 throw 出去
        // TODO 暂时用SourceExeption 之后将这一部分抽出去
        try {
            HttpResponse httpResponse   = ApacheHttpClient.request(targetUrl, method.toUpperCase(), requestData, headerParameters, timeout);
            int status                  = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity           = httpResponse.getEntity();
            String responseBody         = (entity != null) ? EntityUtils.toString(entity) : null;

            if (status >= 200 && status < 300) {
                JSONObject responseBodyJson = JSON.parseObject(responseBody);
                return new ResponseMessage(responseBodyJson.get("data"), responseBodyJson.get("message").toString(), status);
            } else {
                context.getLogger().error("Loading URI:" + targetUrl + " status:" + status + " responseBody:" + responseBody);
                JSONObject responseBodyJson;
                try {
                    responseBodyJson = JSON.parseObject(responseBody);
                } catch (Exception ex) {
                    // response body info
                    throw new SourceBizException("", status);
                }
                throw new SourceBizException(responseBodyJson.get("message").toString(), status);
            }
        } catch (IOException ex) {
            context.getLogger().error(ex.getMessage());
            throw new SourceException(ex);
        }
    }




    /**
     * 初始化 traceId
     * TODO UUID 生成32位，可以改为更短而不失去精度, http://www.iteye.com/topic/1134781
     * @return String
     */
    public String initTraceId() {
        String traceId = getTraceId();
        if (traceId.isEmpty()) {
            traceId = DEFAULT_TRACE_ID_PREFIX + UUID.randomUUID().toString();
            setHeaderParameter(HEADER_FIELD_TRACE_ID, traceId);
        }
        return traceId;
    }

    /**
     * traceId 追踪标识
     * traceId 是用于追踪日志用的标识字符串, 通过header X-Gomeplus-Trace-Id或 url query traceId传递, header 和URL并存时,以URL为准.
     * @return String|""
     */
    public String getTraceId() {
        String traceId = (String)url.getQuery().get(QUERY_FIELD_TRACE_ID);
        if (null != traceId) return traceId;
        traceId = getHeaderParameter(HEADER_FIELD_TRACE_ID);
        if (null != traceId) return traceId;
        traceId = getHeaderParameter(HEADER_FIELD_TRACE_ID_LOWERCASE);
        if (null != traceId) return traceId;
        return "";
    }

    //TODO url
    public void setTraceId(String newtraceId) {
        if (url.getQuery().containsKey(QUERY_FIELD_TRACE_ID)) {
            url.getQuery().put(QUERY_FIELD_TRACE_ID, newtraceId);
        } else {
            setHeaderParameter(HEADER_FIELD_TRACE_ID, newtraceId);
        }
    }

    public String getJsonp() {
        return (String)url.getQuery().get("jsonp");
    }






    private void setHeaderParameter(String key, String value) {
        headerParameters.put(key, value);
    }
    private String getHeaderParameter(String key) {
        return headerParameters.get(key);
    }

































    // getters and setters
    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, String> getHeaderParameters() {
        return headerParameters;
    }

    public void setHeaderParameters(HashMap<String, String> headerParameters) {
        this.headerParameters = headerParameters;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
