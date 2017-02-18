package com.erdaoya.springcloud.comx.utils.config;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by xue on 12/6/16.
 */
public class Loader {
    /**
     * @params String fileName
     * @return Config
     * @throws ConfigException
     */
    public static Config fromJsonFile(String fileName) throws ConfigException{
        String jsonString = "";
        try {
            byte[] data = Files.readAllBytes(Paths.get(fileName));
            jsonString = new String(data);
        } catch (IOException ex) {
            //ex.printStackTrace();
            throw new ConfigException("failed to read json file:" + fileName);
        }
        return fromJson(jsonString);
    }

    /**
     * @params String jsonString
     * @return Config
     * @throw ConfigException
     */
    public static Config fromJson(String jsonString) throws ConfigException{
        try {
            JSONObject data = JSON.parseObject(jsonString);
            return new Config(data);
        }catch (JSONException ex) {
            //ex.printStackTrace();
            throw new ConfigException("bad format json");
        }
    }

    public static void main(String[] args) throws ConfigException{
        Config config = Loader.fromJson("{'test':{'a':'b'}}");

    }
}
