package com.erdaoya.springcloud.comx.utils.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
/**
 * Created by xue on 12/8/16.
 */
public class ConfigTest2 {
    @Test
    public void testConfig() {
        String jsonStr  = "{'test':'testval'}";
        JSONObject jobj = JSON.parseObject(jsonStr);
        Config config   = new Config(jobj);
        Config config2  = new Config(jobj);
        assertEquals("new config data not equal", config.rawData(), config2.rawData());
    }


    @Test
    public void testrInteger() throws ConfigException{
        String jsonStr  = "{'test0':'3', 'test1':3, 'test2':'1 ', 'test3': 'test3', 'test4':['a', 'b']}";
        JSONObject jobj  = JSON.parseObject(jsonStr);
        Config config = new Config(jobj);
        assertEquals("config intvalue not equal", (long)3, (long)config.rintvalue("test0"));
        assertEquals("config intvalue not equal", (long)3, (long)config.rintvalue("test1"));
        try {
            config.rintvalue("test2");
            Assert.fail("should throw ConfigException");
        } catch(ConfigException ex) {
            assertEquals("config exception not equal","Config get Integer type error(converting string to integer). key:test2 config.dataObject:{\"test4\":[\"a\",\"b\"],\"test2\":\"1 \",\"test3\":\"test3\",\"test0\":\"3\",\"test1\":3}", ex.getMessage());
        }
        assertNotEquals("config intvalue default not equal", (long)3, (long)config.intvalue("test111", 0));
        try {
            config.rintvalue("test4");
            Assert.fail("should throw ConfigException");
        } catch(ConfigException ex) {
            assertEquals("config exception not equal",  "Config get Integer type error. key:test4 config.dataObject:{\"test4\":[\"a\",\"b\"],\"test2\":\"1 \",\"test3\":\"test3\",\"test0\":\"3\",\"test1\":3}", ex.getMessage());
        }
    }

    @Test
    public void testBool() throws ConfigException{
        String jsonStr  = "{'test0':true, 'test1':'true', 'test2':1, 'test3': 'test3', 'test4':['a', 'b']}";
        JSONObject jobj  = JSON.parseObject(jsonStr);
        Config config = new Config(jobj);
        assertEquals("config intvalue not equal", config.bool("test0", false), true);
        assertEquals("config intvalue not equal", config.bool("test1", false), true);
        assertEquals("config intvalue default not equal",false,  config.bool("test111", false));
        try {
            config.rbool("test4");
            Assert.fail("should throw ConfigException");
        } catch(ConfigException ex) {
            assertEquals("config exception not equal",  "Config get Boolean type error. key:test4 config.dataObject:{\"test4\":[\"a\",\"b\"],\"test2\":1,\"test3\":\"test3\",\"test0\":true,\"test1\":\"true\"}", ex.getMessage());
        }
    }

    @Test
    public void testStr() throws ConfigException{
        String jsonStr  = "{'test0':true, 'test1':'true', 'test2':2, 'test3': 'test3', 'test4':['a', 'b']}";
        JSONObject jobj  = JSON.parseObject(jsonStr);
        Config config = new Config(jobj);
        assertEquals("config intvalue not equal", config.rstr("test0"), "true");
        assertEquals("config intvalue not equal", config.rstr("test1"), "true");
        assertEquals("config intvalue default not equal","test111",  config.str("test111", "test111"));
        try {
            config.rstr("test4");
            Assert.fail("should throw ConfigException");
        } catch(ConfigException ex) {
            assertEquals("config exception not equal",  "Config get String type error. key:test4 config.dataObject:{\"test4\":[\"a\",\"b\"],\"test2\":2,\"test3\":\"test3\",\"test0\":true,\"test1\":\"true\"}", ex.getMessage());
        }
    }











}






