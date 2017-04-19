package com.erdaoya.springcloud.comx.schema.datadecor.decors;

import com.erdaoya.springcloud.comx.source.Source;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.Config;

import java.util.*;

/**
 * Created by xue on 12/20/16.
 */
public class EachDecor extends AbstractDecor implements RefJsonPath{
    public static final String FIELD_SOURCE         = "source";
    public static final String FIELD_FIELD          = "field";

    protected Source source;
    protected String field;

    public EachDecor(Config conf){
        super(conf);
    }
    public String getType(){ return AbstractDecor.TYPE_EACH;}



    public void doDecorate(Object data, Context context) throws ConfigException, SourceException{
        List matchedNodes = getMatchedNodes(conf, data, context);
        source = new Source(conf.rsub(EachDecor.FIELD_SOURCE));
        field  = conf.str(EachDecor.FIELD_FIELD,  "");

        // TODO 针对简单source load 情况， 内部应不会产生数据冲突， 我们可以采用 parallel 或者 fiber 形式都可以
        //for (Object ref: matchedNodes) {
        //    this.decorateOneNode(ref, data, context);
        //}
        // 其实ref 是一个 map (之后在最前面约束ref 为 map)
        ArrayList<Exception> exceptions = new ArrayList<>();
        matchedNodes.parallelStream().forEach(ref -> {
            try {
                decorateOneNode(ref, data, context);
            } catch(Exception ex) {
                exceptions.add(ex);
            }
        });
        if (exceptions.isEmpty()) return;
        Exception ex = exceptions.get(0);
        if (ex instanceof SourceException) throw (SourceException) ex;
        throw new SourceException(exceptions.get(0));
    }


    /**
     * 调用Source loadData; TODO 修正逻辑
     * @param ref
     * @param data
     * @param context
     */
    public void decorateOneNode(Object ref, Object data, Context context) throws ConfigException, SourceException{
        HashMap<String, Object> vars = new HashMap<String, Object>(){{
                put("ref", ref);
                put("data", data);
            }
        };
        Object loaded = source.loadData(context, vars);
        if (null == loaded) return;

        Map obj = field.isEmpty()? (Map) loaded: new HashMap<String, Object>(){{put(field, loaded);}};
        if (ref instanceof Map) {
            for (Object key: obj.keySet()) {
                ((Map)ref).put(key, obj.get(key));
            }
        }
    }
}