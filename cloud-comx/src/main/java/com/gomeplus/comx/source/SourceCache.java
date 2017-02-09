//package com.gomeplus.comx.source;
//
//import com.gomeplus.comx.utils.config.Config;
//import com.gomeplus.comx.utils.config.ConfigException;
//
//import java.util.HashMap;
//
///**
// * Created by xue on 12/28/16.
// */
//public class SourceCache {
//    public static final String FIELD_KEY    = "key";
//    public static final String FIELD_TTL    = "ttl";
//    public static final String KEY_PREFIX   = "Source:";
//
//
//    protected String    key;
//    protected Integer   ttlMs;
//
//    public SourceCache(String key, Integer ttlMs) {
//        this.key = key;
//        this.ttlMs = ttlMs;
//    }
//
//    public static SourceCache create(Config config, String key) throws ConfigException{
//        if (config.rawData().isEmpty()) return new NullSourceCache();
//        return new SourceCache(key, config.rintvalue(FIELD_TTL));
//    }


//}
