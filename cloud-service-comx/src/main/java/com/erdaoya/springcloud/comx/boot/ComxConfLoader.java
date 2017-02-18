package com.erdaoya.springcloud.comx.boot;

import com.erdaoya.springcloud.comx.utils.cache.AbstractCache;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.source.sourcebase.SourceBaseFactory;
import com.erdaoya.springcloud.comx.utils.config.Loader;
import com.erdaoya.springcloud.comx.utils.cache.CacheFactory;
import com.erdaoya.springcloud.comx.utils.config.Config;

/**
 * Created by xue on 12/16/16.
 * 在服务启动时加载配置
 */
public class ComxConfLoader {
    private static boolean initialized = false;
    private static String COMX_HOME;
    private static Config comxConf;
    private static SourceBaseFactory sourceBaseFactory;
    private static AbstractCache cache;
    private static String urlPrefix;



    private static final String FIELD_URL_PREFIX    = "urlPrefix";
    private static final String FIELD_CACHE         = "cache";



    // synchronized TODO
    // 提供更新方法 TODO
    public static Config load()  throws ConfigException {
        initialize();
        if (!initialized) {
            initialize();
        }
        return comxConf;
    }

    public static void initialize()  throws ConfigException{
        COMX_HOME = "/www/comx-conf";
        String comxConfFile = COMX_HOME + "/comx.conf.json";
        comxConf            = Loader.fromJsonFile(comxConfFile);
        sourceBaseFactory   = SourceBaseFactory.fromConf(comxConf);
        urlPrefix           = comxConf.rstr(FIELD_URL_PREFIX);
        Config cacheConf    = comxConf.sub(FIELD_CACHE);
        cache               = CacheFactory.fromConf(cacheConf);
        initialized         = true;
    }






    public static String getUrlPrefix() {
        return urlPrefix;
    }

    public static AbstractCache getCache() {return cache;}

    public static String getComxHome() {
        return COMX_HOME;
    }

    public static Config getComxConf() {
        return comxConf;
    }

    public static SourceBaseFactory getSourceBaseFactory() {
        return sourceBaseFactory;
    }
}
