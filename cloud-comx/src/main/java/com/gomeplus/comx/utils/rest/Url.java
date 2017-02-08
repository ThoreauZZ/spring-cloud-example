package com.gomeplus.comx.utils.rest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.net.*;
import java.io.*;

import static org.apache.http.Consts.ISO_8859_1;
import static org.apache.http.Consts.UTF_8;


/**
 * Created by xue on 12/19/16.
 * TODO 寻找适合的 url 解析以及请求类
 * TODO 实现map 细节再思考
 * TODO 同时考虑 client, netty and quasar fiber 写法
 * 暂时用手写解析
 */
public class Url implements ArrayAccessBase{
    private   String    url;
    private   URI       aURI;
    private   UrlQuery  query;


    public Url(String url) throws UrlException{
        this.url = url;
        try {
            aURI = new URI(url);
            List<NameValuePair> list = URLEncodedUtils.parse(aURI, "UTF-8");
            HashMap<String, String> parsedParameters = new HashMap<>();
            for (NameValuePair pair:list) {
                parsedParameters.put(pair.getName(), pair.getValue());
            }
            //System.out.println(list);
            query = new UrlQuery(aURI.getQuery(), parsedParameters);
        }catch(Exception ex) {
            throw new UrlException(ex);
        }
    }

    /**
     *  包含
     *  query           return query;
     *  url             return url;
     *  queryString     URI getQuery
     *  path            URI getPath
     *  host            URI getHost
     *  scheme          URI getScheme
     *  port            URI getPort
     *  hash|fragment   兼容（hash）
     *  user
     *  pass
     *  portWithDefaultValue
     */

    // implements interface TODO
    public boolean containsKey(Object key) {
        return true;
    }
    // user & pass 暂时不实现    hash 即 fragment
    public Object get(Object key) {
        switch ((String)key) {
            case "url":             return url;
            case "query":           return query;
            case "queryString":     return aURI.getQuery();
            case "path":            return aURI.getPath();
            case "host":            return aURI.getHost();
            case "scheme":          return aURI.getScheme();
            case "port":            return aURI.getPort();
            case "fragment":        return aURI.getFragment();
            case "hash":            return aURI.getFragment();
            case "userInfo":        return aURI.getUserInfo();
        }
        return null;
    }


    /**
     * @param parameters                 hashmap<String, String> queryparameters to merge
     * @param reserveOriginalParameters  Boolean  whether to reserve original params
     * @return A new Url Object;
     */
    public Url mergeQueryParameters(HashMap<String, String> parameters, boolean reserveOriginalParameters) throws UrlException{
        HashMap<String, String> originParameters = query.getParameters();
        HashMap<String, String> map3 = new HashMap<>();
        if (reserveOriginalParameters) {
            map3.putAll(parameters);
            map3.putAll(originParameters);
        } else {
            map3.putAll(originParameters);
            map3.putAll(parameters);
        }
        try {
            String urlStr = regenerateUrlStringWithParametersStr(map3);
            return new Url(urlStr);
        } catch (UnsupportedEncodingException ex) {
            throw  new UrlException(ex);
        }
    }

    protected String regenerateUrlStringWithParametersStr(HashMap<String, String> map) throws UnsupportedEncodingException{
        String queryString = aURI.toString().split("\\?")[0];

        List<NameValuePair> queryList = new ArrayList<NameValuePair>();
        for (String key:map.keySet()) {
            queryList.add(new BasicNameValuePair(key, map.get(key)));
        }
        return queryString + "?" + URLEncodedUtils.format(queryList, UTF_8);
    }


    /**
     * get related path
     * @param urlPrefix
     * @return
     * @throws UrlException
     */
    public String getRelatedPath(String urlPrefix) throws UrlException{
        Url prefix = new Url(urlPrefix);
        if (!prefix.get("host").equals(this.get("host"))) {
            throw new UrlException("UrlException: failed to get related path: unmatched host. prefix["+ urlPrefix + "]; URL:[" + getUrl() + "]");
        }
        if (!prefix.getPortWithDefaultValue().equals(this.getPortWithDefaultValue())) {
            throw new UrlException("UrlException: failed to get related path: unmatched port. prefix["+ urlPrefix + "]; URL:[" + getUrl() + "]");
        }

        String sourcePath = (String)this.get("path");
        String prefixPath = (String)prefix.get("path");
        if ("".equals(prefixPath)) {
            return sourcePath;
        }
        if (sourcePath.indexOf(prefixPath) != 0) {
            throw new UrlException("UrlException: fail to get related path. unmatched path: PREFIX[" + urlPrefix + "]; URL[" + url + "]");
        }
        return sourcePath.substring(prefixPath.length());
    }

    /**
     * default port;
     * if defined,          return defined;
     * else if has default, return default;
     * else return -1;
     * @return default port integer
     */
    protected Integer getPortWithDefaultValue() {
        Integer port = (Integer) get("port");
        if (!port.equals(-1)) return port;
        if (get("scheme").equals("http"))   return 80;
        if (get("scheme").equals("https"))  return 443;
        return -1;
    }



    public UrlQuery getQuery() {
        return query;
    }

    public String getUrl() {
        return url;
    }
    public URI getaURI() {
        return aURI;
    }
}
