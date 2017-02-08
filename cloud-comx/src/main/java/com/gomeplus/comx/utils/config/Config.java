package com.gomeplus.comx.utils.config;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by xue on 12/6/16.
 * 需要分辨 dataObject or dataArray
 *
 */
public class Config {
    protected JSONObject dataObject;
    //protected JSONArray  dataArray;
    protected HashMap<String, Config> subs = new HashMap<>();

    public Config(JSONObject dataObject)      {this.dataObject = dataObject;}
    //public Config(JSONArray dataArray)  {this.dataArray = dataArray;}







    /**
     * TODO str & rstr 修正逻辑
     * @param key           key
     * @param defaultValue 默认值
     * @return String
     */
    public String str(String key, String defaultValue){
        try {
            return rstr(key);
        }catch (ConfigException ex) {
            return defaultValue;
        }
    }
    public String rstr(String key) throws ConfigException{
        if (!dataObject.containsKey(key)) throw new ConfigException("Config get rstr failed. key:"+ key + " config.dataObject:"+ dataObject);
        Object value = dataObject.get(key);
        if (value instanceof Boolean) return value.toString();
        if (value instanceof Integer) return value.toString();
        if (value instanceof String)  return value.toString();
        throw new ConfigException("Config get String type error. key:" + key + " config.dataObject:" + dataObject);
    }














    public boolean bool(String key, boolean defaultValue) {
        try {
            return rbool(key);
        } catch (ConfigException ex){
            return defaultValue;
        }
    }

    public boolean rbool(String key) throws ConfigException{
        if (!dataObject.containsKey(key)) throw new ConfigException("Config get Boolean type error. key:" + key + " config.dataObject:" + dataObject);
        Object value = dataObject.get(key);
        if (value instanceof Boolean) return (Boolean)value;
        if (value instanceof Integer) return !(value.equals(0));
        if (value instanceof String)  return !(value.equals("false"));
        throw new ConfigException("Config get Boolean type error. key:" + key + " config.dataObject:" + dataObject);
    }

    public Integer intvalue(String key, Integer defaultValue){
        try {
            return rintvalue(key);
        } catch (ConfigException ex){
            return defaultValue;
        }
    }

    public Integer rintvalue(String key) throws ConfigException{
        if (!dataObject.containsKey(key)) throw new ConfigException("Config get Integer type error. key:" + key + "config.dataObject:" + dataObject);
        Object value = dataObject.get(key);
        if (value instanceof Boolean) return ((Boolean)value)?1:0;
        if (value instanceof Integer) return (Integer)value;
        if (value instanceof String)  {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException ex) {
                throw new ConfigException("Config get Integer type error(converting string to integer). key:" + key + " config.dataObject:" + dataObject);
            }
        }
        throw new ConfigException("Config get Integer type error. key:" + key + " config.dataObject:" + dataObject);
    }












    public Set<String> keys() {
        return dataObject.keySet();
    }






    public Config rsub(String key) throws ConfigException{
        if (!subs.containsKey(key)) subs.put(key, this.genSub(key, true));
        return subs.get(key);
    }
    public Config sub(String key) throws ConfigException{
        if (!subs.containsKey(key)) subs.put(key, this.genSub(key, false));
        return subs.get(key);
    }

    public Config genSub(String key, boolean restrict)  throws ConfigException{
        if (!(dataObject.containsKey(key))) {
            if (restrict) {
                throw new ConfigException("sub node does not exist. FIELD[" + key + "]");
            }
            return new Config(new JSONObject());
        }
        Object arr = dataObject.get(key);
        if (arr instanceof JSONObject){
            return new Config((JSONObject) arr);
        }
        if (arr instanceof JSONArray){
            // 将array 转化为 map
            Object[] s = ((JSONArray) arr).toArray();
            JSONObject tdataObject = new JSONObject();
            for (Integer index = 0; index < s.length; index++){
                tdataObject.put(index.toString(), s[index]);
            }
            return new Config(tdataObject);
        }
        throw new ConfigException("type error, expects array or object. FIELD[" + key + "]" + "found: " + arr);
    }








    public JSONObject rawData() {return this.dataObject;}
}
