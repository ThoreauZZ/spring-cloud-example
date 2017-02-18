package com.gomeplus.comx.utils.rest;

import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by xue on 1/16/17.
 */
public class UrlTest {
    @Test
    public void testsuccess() throws Exception {
        String urlstr = "https://username:password@a.b.com:8833/p/a/t/h?query=test&中文query=中文测试#fragment";
        //urlstr = "http://a.b.com/p/a/t/h?query=test&中文query=中文测试#fragment";
        Url url = new Url(urlstr);
        assertEquals("not equal", url.get("scheme"), "https");
        assertEquals("not equal", url.get("host"), "a.b.com");
        assertEquals("not equal", url.get("port"), new Integer(8833));
        assertEquals("not equal", url.get("path"), "/p/a/t/h");
        assertEquals("not equal", url.get("fragment"), "fragment");
        assertEquals("not equal", url.get("hash"), "fragment");
        assertEquals("not equal", url.get("queryString"), "query=test&中文query=中文测试");
        assertEquals("not equal", url.get("userInfo"), "username:password");
    }


    @Test
    public void testRelatedPath() throws UrlException, Exception{
        String urlstr = "https://username:password@a.b.com/p/a/t/h?query=test&query3=" + URLEncoder.encode("中文测试value", "UTF-8");
        String prefixUrl = "https://username:password@a.b.com/p/a";
        Url aUrl = new Url(urlstr);
        String testpath = aUrl.getRelatedPath(prefixUrl);
        assertEquals("not equal", testpath, "/t/h");
    }

    @Test
    public void testgenerateUrl() throws UnsupportedEncodingException, UrlException{
        String urlstr = "https://username:password@a.b.com:8833/p/a/t/h?query=test&query3=" + URLEncoder.encode("中文测试value", "UTF-8");
        //urlstr = "http://a.b.com/p/a/t/h?query=test&中文query=中文测试#fragment";
        Url url = new Url(urlstr);
        HashMap<String, String> params= new HashMap<>();
        params.put("query",  "value");

        assertEquals("not equal", "https://username:password@a.b.com:8833/p/a/t/h?query=value", url.regenerateUrlStringWithParametersStr(params));
        assertEquals("not equal", "https://username:password@a.b.com:8833/p/a/t/h?query=test&query3=%E4%B8%AD%E6%96%87%E6%B5%8B%E8%AF%95value", url.mergeQueryParameters(params, true).getUrl());
        assertEquals("not equal", "https://username:password@a.b.com:8833/p/a/t/h?query=value&query3=%E4%B8%AD%E6%96%87%E6%B5%8B%E8%AF%95value", url.mergeQueryParameters(params, false).getUrl());



    }
}





