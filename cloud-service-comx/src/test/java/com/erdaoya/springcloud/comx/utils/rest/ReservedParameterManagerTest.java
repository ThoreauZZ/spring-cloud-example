package com.erdaoya.springcloud.comx.utils.rest;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by xue on 1/24/17.
 */
public class ReservedParameterManagerTest {
    @Test
    public void test() throws UrlException{
        HashMap<String, String> headerparams  = new HashMap<>();
        headerparams.put("X-Gomeplus-Login-Token", "aaaa");
        headerparams.put("x-gomeplus-user-id", "userIdA");
        Url url = new Url("http://t.e.s.t.com/p/a/t/h?query1=test&loginToken=bbbb");
        String method = "put";
        HashMap<String, Object> data  = new HashMap<>();
        data.put("data1", "datavalue");

        RequestMessage requestMessage = new RequestMessage(url, method, data, headerparams, 0);

        ReservedParameterManager reservedParameterManager = ReservedParameterManager.fromRequest(requestMessage);
        //System.out.println(reservedParameterManager.getReservedQueryParams());
        //System.out.println(reservedParameterManager.getFilteredReservedHeaders());
        assertEquals("not equal", "bbbb", reservedParameterManager.getLoginToken());
        assertEquals("not equal", "userIdA", reservedParameterManager.getUserId());
        assertEquals("not equal", null, reservedParameterManager.getApp());
    }
}
