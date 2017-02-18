package com.erdaoya.springcloud.comx.source;

import com.alibaba.fastjson.JSONPath;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.sourcebase.AbstractSourceBase;
import com.erdaoya.springcloud.comx.source.sourcebase.UnmatchedRequestMethodException;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.schema.TinyTemplate;
import com.erdaoya.springcloud.comx.schema.onError.Strategy;
import com.erdaoya.springcloud.comx.source.sourcebase.SourceBaseFactory;
import com.erdaoya.springcloud.comx.utils.config.Config;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xue on 12/22/16.
 */
public class Source {
    static final Integer DEFAULT_TIMEOUT            = 8000;
    static final String FIELD_BASE                  = "base";
    static final String FIELD_FIRST_ENTRY_ONLY      = "firstEntryOnly";
    static final String FIELD_JSON_PATH             = "jsonPath";
    static final String FIELD_ON_ERROR              = "onError";
    static final String FIELD_BACKUP                = "backup";

    public static final String FIELD_URI            = "uri";
    public static final String FIELD_METHOD         = "method";
    public static final String FIELD_TIMEOUT        = "timeout";
    public static final String RESERVED_RENDERED_URI= "reserved_rendered_uri";
    static final String RESERVED_TPL_VAR_REQUEST    = "request";



    static final String FIELD_CACHE                 = "cache";
    static final String SOURCE_CACHE_KEY_PREFIX     = "Source:";
    static final String SOURCE_CACHE_TTL            = "ttl:";
    static final String SOURCE_CACHE_KEY            = "key";
    static final Integer SOURCE_CACHE_TTL_DEFAULT   = 300;


    /**
     * source 是一个对应的source 实例， 提供loadData 返回 Map Object;(或非Map)
     * 它将使用 sourcebase 资源以及 sourceCache localCache 缓存
     * 同一个source 应对refJsonPath 不同情况下拥有不同运行结果，所以不能是object 属性
     * 不拥有变量 reservedVariables
     * renderedUri 会随着 reservedVariables 变化而不断变化
     * 一个source 是一个最小单位，所以可以直接在variables 添加renderedUri
     */
    private     Config                    conf;
    public      Source(Config conf) { this.conf = conf;}

    /**
     * loadData 外层 记录日志
     * @param context   context
     * @param reservedVariables {ref|request|data|renderedUri} TODO renderedUri 是否在这里存疑
     * @return Object data
     */
    public Object loadData(Context context, HashMap<String, Object> reservedVariables) throws ConfigException, SourceException{
        // since redis operation, choose nanotime; 1E-9
        long        time0   = System.nanoTime();
        Object      result  = null;
        Exception   ex      = null;

        context.getLogger().debug("Source loading URI:" + this.getUri());
        try {
            result           = doLoadData(context, reservedVariables);
            double deltaTime = (System.nanoTime() - time0) * 1.0 / 1000_000_000;
            context.getLogger().debug("Source loading used:" + deltaTime + " sec, URI:" + this.getUri());
        } catch (Exception ex0){
            ex = ex0;
        }

        if (result != null) return result;
        Config backupConf = conf.sub(Source.FIELD_BACKUP);
        if (backupConf.rawData().isEmpty()) {
            context.getLogger().error("Source loading backupConf empty; URI:" + this.getUri());
            if (ex != null)     throw new SourceException(ex);
            return null;
        }
        Source backSource = new Source(backupConf);
        return backSource.loadData(context, reservedVariables);
    }


    // TODO 确认 cache 中是否可以自设置 key
    // cache 包含key 都是属于 decorcache
    // TODO 再次确认 request 是否可以放在外层
    private Object doLoadData(Context context, HashMap<String, Object> reservedVariables) throws  IOException, UnmatchedRequestMethodException, Exception{
        try {
            String uri          = getUri();
            TinyTemplate tpl    = new TinyTemplate(uri);
            reservedVariables.put(RESERVED_TPL_VAR_REQUEST, context.getRequest());
            String renderedUri  = tpl.render(reservedVariables, context);
            reservedVariables.put(RESERVED_RENDERED_URI, renderedUri);

            Object data = loadCache(context, renderedUri);
            if (data == null) {
                data = getBase(context).executeLoading(context, conf, reservedVariables);
                setCache(context, renderedUri, data);
            }

            return postHandle(data);
        } catch (Exception ex) {
            context.getLogger().error("Source loading doLoadData error:" + ex.getMessage() + "; " + ex.getClass());
            //TODO 记录详细日志 方式
            ex.printStackTrace();
            return Strategy.fromConf(conf.sub(FIELD_ON_ERROR)).handleSourceException(ex, context);
        }
    }



    public AbstractSourceBase getBase(Context context) throws ConfigException{
        String baseId = conf.str(Source.FIELD_BASE, "default");
        return SourceBaseFactory.getSourceBase(baseId);
    }

    // get cache 失败不影响流程继续进行， 但应加入日志
    private Object loadCache(Context context, String renderedUri) throws ConfigException{
        // localcache 部分
        String localCacheKey = this.getBase(context).getId() + ":" + renderedUri;
        if (context.getLocalCacheEnabled() && context.getLocalCache().containsKey(localCacheKey)) {
            return context.getLocalCache().get(localCacheKey);
        }
        // sourcecache 部分
        try {
            Config cacheConf = conf.sub(FIELD_CACHE);
            if (cacheConf.rawData().isEmpty()) return null;
            return context.getCache().getMapObject(SOURCE_CACHE_KEY_PREFIX + renderedUri);
        } catch (Exception ex) {
            context.getLogger().error("Source loading: getCache error:" + ex.getMessage() + "; " + ex.getClass());
            // TODO 是否如此记录
            ex.printStackTrace();
            return null;
        }
    }

    // set cache 失败不影响流程继续进行， 但应加入日志
    private void setCache(Context context, String renderedUri, Object data) throws ConfigException {
        // localcache 部分
        String localCacheKey = this.getBase(context).getId() + ":" + renderedUri;
        if (context.getLocalCacheEnabled()) context.getLocalCache().put(localCacheKey, data);
        // sourcecache 部分
        try {
            String key = SOURCE_CACHE_KEY_PREFIX + renderedUri;
            context.getLogger().trace("Source loading: setCache key:" + key);
            Config cacheConf = conf.sub(FIELD_CACHE);
            if (cacheConf.rawData().isEmpty()) return;
            Integer ttl = cacheConf.intvalue(SOURCE_CACHE_TTL, SOURCE_CACHE_TTL_DEFAULT);
            if (data instanceof Map) {
                context.getCache().setMapObject(key, (Map) data, ttl);
            } else {
                context.getCache().set(key, data.toString(), ttl);
            }
        } catch (Exception ex) {
            context.getLogger().error("Source loading: setCache error:" + ex.getMessage() + "; " + ex.getClass());
            // TODO 是否如此记录
            ex.printStackTrace();
        }
    }
















    protected Object postHandle(Object data) {
        String jsonPath = conf.str(FIELD_JSON_PATH, "");
        if (jsonPath.isEmpty()) return data;
        Object matchedNode = JSONPath.eval(data, jsonPath);
        if (conf.bool(FIELD_FIRST_ENTRY_ONLY, false)) {
            return (matchedNode.getClass().isArray())? Array.get(matchedNode, 0):null;
        }
        return matchedNode;
    }

    protected String getUri() throws ConfigException{
        return conf.rstr(Source.FIELD_URI);
    }

}
