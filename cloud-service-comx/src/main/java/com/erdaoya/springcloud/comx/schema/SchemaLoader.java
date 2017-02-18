package com.erdaoya.springcloud.comx.schema;

import com.erdaoya.springcloud.comx.boot.ComxConfLoader;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.config.Loader;

/**
 * Created by xue on 12/16/16.
 */
public class SchemaLoader {
    public static Schema load(String urlPath, String method) throws ConfigException{
        String pathfile = ComxConfLoader.getComxHome() + "/apis" + urlPath;
        Config conf = Loader.fromJsonFile(pathfile+ "/"+ method + ".json");
        Schema schema = new Schema(conf);
        return schema;
    }
}
