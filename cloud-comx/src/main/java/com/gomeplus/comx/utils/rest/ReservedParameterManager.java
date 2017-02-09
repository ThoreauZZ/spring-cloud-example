package com.gomeplus.comx.utils.rest;

import com.gomeplus.comx.context.Context;

import java.util.HashMap;

/**
 * Created by xue on 12/23/16.
 * 部分请求参数保留到调用接口的类
 * 以此类废弃 commonParameterHolder
 * TODO 将配置部分抽取出来，读取配置
 */
public class ReservedParameterManager {

    private static final HashMap<String, String> RESERVED_PARAMETERS = new HashMap<String, String>(){
        {
            put("loginToken",   "X-Gomeplus-Login-Token"    );
            put("userId",       "X-Gomeplus-User-Id"        );
            put("accessToken",  "X-Gomeplus-Access-Token"   );
            put("device",       "X-Gomeplus-Device"         );
            put("app" ,         "X-Gomeplus-App"            );
            put("net",          "X-Gomeplus-Net"            );
            put("accept",       "Accept"                    );
            put("traceId",      "X-Gomeplus-Trace-Id"       );
            put("appVersion",   "X-Gomeplus-App-Version"    );
        }
    };
    private static final HashMap<String, String> RESERVED_TO_QUERY_PARAMERTERS = new HashMap<String, String>(){
        {
            put("uniqueDeviceId",  "X-Gomeplus-Unique-Device-Id");
        }
    };




    protected HashMap<String, String> reservedQueryParams;
    protected HashMap<String, String> reservedHeaderParams;

    /**
     * 1, query to query, header to header
     * 2, reserved to query. (header and query to query);
     * if both key exists, choose query;
     * @param request RequestMessage
     * @return ReservedParameterManager
     */
    public static ReservedParameterManager fromRequest(RequestMessage request) {
        HashMap<String, String> headers = request.getHeaderParameters();
        HashMap<String, String> queries = request.getUrl().getQuery().getParameters();

        HashMap<String, String> reservedHeaderParams = new HashMap<>();
        HashMap<String, String> reservedQueryParams  = new HashMap<>();

        for (String queryName: RESERVED_PARAMETERS.keySet()) {
            String headerName = RESERVED_PARAMETERS.get(queryName);
            if (headers.containsKey(headerName)) {
                reservedHeaderParams.put(queryName, headers.get(headerName));
            }
            if (queries.containsKey(queryName)) {
                reservedQueryParams.put(queryName, queries.get(queryName));
            }
        }
        for (String queryName: RESERVED_TO_QUERY_PARAMERTERS.keySet()) {
            String headerName = RESERVED_PARAMETERS.get(queryName);
            if (headers.containsKey(headerName)) {
                reservedQueryParams.put(queryName, queries.get(headerName));
            }
            if (queries.containsKey(queryName)) {
                reservedQueryParams.put(queryName, queries.get(queryName));
            }
        }
        return new ReservedParameterManager(reservedQueryParams, reservedHeaderParams);
    }

    public ReservedParameterManager(HashMap<String, String> reservedQueryParams, HashMap<String, String> reservedHeaderParams) {
        this.reservedHeaderParams = reservedHeaderParams;
        this.reservedQueryParams  = reservedQueryParams;
    }

    public HashMap<String, String> getReservedQueryParams(){
        return reservedQueryParams;
    }

    // TODO 初始化之时直接将query和header中的冲突参数merge，以query为主
    public HashMap<String, String> getFilteredReservedHeaders(Context context) {
        HashMap<String, String> result = new HashMap<>();
        for (String queryName: reservedHeaderParams.keySet()) {
            String headerName = RESERVED_PARAMETERS.get(queryName);
            if (reservedQueryParams.containsKey(headerName)){
                continue;
            }
            result.put(headerName, reservedHeaderParams.get(queryName));
        }
        //context.getLogger().error("In Reserved Param manager: " + result.keySet());
        return result;
    }



    //TODO
    public String getUserId() {
        String headerKey = "X-Gomeplus-User-Id";
        String queryKey  = "userId";
        if (reservedQueryParams.containsKey(queryKey))    return reservedQueryParams.get(queryKey);
        if (reservedHeaderParams.containsKey(queryKey))  return reservedHeaderParams.get(queryKey);
        return null;
    }

    public String getLoginToken() {
        String headerKey = "X-Gomeplus-Login-Token";
        String queryKey  = "loginToken";
        if (reservedQueryParams.containsKey(queryKey))    return reservedQueryParams.get(queryKey);
        if (reservedHeaderParams.containsKey(queryKey))  return reservedHeaderParams.get(queryKey);
        return null;
    }

    public String getApp() {
        String headerKey = "X-Gomeplus-App";
        String queryKey  = "app";
        if (reservedQueryParams.containsKey(queryKey))    return reservedQueryParams.get(queryKey);
        if (reservedHeaderParams.containsKey(queryKey))  return reservedHeaderParams.get(queryKey);
        return null;
    }
}




