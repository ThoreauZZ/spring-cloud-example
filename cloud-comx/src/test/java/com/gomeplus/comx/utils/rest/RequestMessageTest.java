package com.gomeplus.comx.utils.rest;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by xue on 1/19/17.
 */
public class RequestMessageTest {
    @Test
    public void test() throws UrlException{
        HashMap<String, String> headerparams  = new HashMap<>();
        headerparams.put("X-Gomeplus-Login-Token", "aaaa");
        Url url = new Url("http://t.e.s.t.com/p/a/t/h?query1=test&loginToken=bbbb");
        String method = "put";
        HashMap<String, Object> data  = new HashMap<>();
        data.put("data1", "datavalue");

        RequestMessage requestMessage = new RequestMessage(url, method, data, headerparams, 0);

        assertEquals("not equal", "put", requestMessage.get("method"));
        Url aurl = (Url)requestMessage.get("url");
        assertEquals("not equal", "/p/a/t/h", aurl.get("path"));
        HashMap adata = (HashMap) requestMessage.get("data");
        assertEquals("not equal", "datavalue", adata.get("data1"));
        HashMap aheader = (HashMap)requestMessage.get("headerParameters");
        assertEquals("not equal", "datavalue", adata.get("data1"));

    }
}
