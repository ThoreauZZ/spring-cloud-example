package com.gomeplus.comx.utils.rest;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by xue on 1/19/17.
 */
public class UrlQueryTest {
    @Test
    public void testurlquery() {
        String queryString = "a=1&b=2";
        HashMap queryhash  = new HashMap();
        queryhash.put("a", "1");
        queryhash.put("b", "2");
        UrlQuery urlQuery = new UrlQuery(queryString, queryhash);

        assertEquals("not equal", "1", urlQuery.get("a"));
        assertEquals("not equal", "2", urlQuery.get("b"));
        assertEquals("not equal", null, urlQuery.get("c"));
        assertEquals("not equal", queryhash, urlQuery.getParameters());
        assertEquals("not equal", queryString, urlQuery.getQueryString());
        assertEquals("not equal", true, urlQuery.containsKey("a"));
        assertEquals("not equal", true, urlQuery.containsKey("b"));
        assertEquals("not equal", false, urlQuery.containsKey("c"));


    }
}
