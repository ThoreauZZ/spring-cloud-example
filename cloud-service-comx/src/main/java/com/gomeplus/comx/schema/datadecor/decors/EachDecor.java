package com.gomeplus.comx.schema.datadecor.decors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.schema.datadecor.DecorException;
import com.gomeplus.comx.source.Source;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xue on 12/20/16.
 */
public class EachDecor extends AbstractDecor {
    public static final String FIELD_SOURCE         = "source";
    public static final String FIELD_FIELD          = "field";
    public static final String FIELD_REF_JSON_PATH  = "refJsonPath";

    protected Source source;
    protected String field;

    public EachDecor(Config conf){
        super(conf);
    }
    public String getType(){ return AbstractDecor.TYPE_EACH;}



    public void doDecorate(Object data, Context context) throws ConfigException, SourceException{
        ArrayList matchedNodes = this.getMatchedNodes(data, context);
        source = new Source(conf.rsub(EachDecor.FIELD_SOURCE));
        field  = conf.str(EachDecor.FIELD_FIELD,  "");

        context.getLogger().debug("decor eachdecor, input data:"+ data.toString());
        //
        for (Object ref: matchedNodes) {
            this.decorateOneNode(ref, data, context);
        }
    }

    /**
     * 记录日志
     * TODO 需要验证 refjsonpath 效果一致
     * @param data
     * @param context 记录日志
     * @return ArrayList
     */
    protected ArrayList getMatchedNodes(Object data, Context context){
        String refJsonPath = conf.str(EachDecor.FIELD_REF_JSON_PATH, null);
        if (null == refJsonPath) {
            return new ArrayList(Arrays.asList(data));
        }
        try {
            Object matchedNode = JSONPath.eval(data, refJsonPath);
            if (matchedNode instanceof ArrayList) {
                return (ArrayList) matchedNode;
            } else {
                return new ArrayList(Arrays.asList(matchedNode));
            }
        } catch(Exception ex){
            context.getLogger().warn("Decor Eachdecor, refJsonPath error, refJsonPath:"+ refJsonPath+ ", data:" + data);
            return new ArrayList(Arrays.asList(data));
        }
    }

    /**
     * 调用Source loadData; TODO 修正逻辑
     * @param ref
     * @param data
     * @param context
     */
    public void decorateOneNode(Object ref, Object data, Context context) throws ConfigException, SourceException{
        HashMap<String, Object> vars = new HashMap<>();
        vars.put("ref", ref);
        vars.put("data", data);
        Object loaded = source.loadData(context, vars);
        Map obj = new HashMap<>();
        if (field.isEmpty()) {
            obj = (Map) loaded;
        } else {
            obj.put(field, loaded);
        }
        if (ref instanceof Map) {
            for (Object key: obj.keySet()) {
                ((Map)ref).put(key, obj.get(key));
            }
        }
    }
}