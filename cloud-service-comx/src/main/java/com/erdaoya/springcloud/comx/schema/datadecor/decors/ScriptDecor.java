package com.erdaoya.springcloud.comx.schema.datadecor.decors;

import com.alibaba.fastjson.JSONPath;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.schema.datadecor.DecorException;
import com.erdaoya.springcloud.comx.utils.config.Config;
import groovy.lang.Binding;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * http://wiki.jikexueyuan.com/project/groovy-introduction/integrating-groovy-applications.html
 * Created by xue on 1/17/17.
 * TODO 勉强能用 需要测试用例
 */
public class ScriptDecor extends AbstractDecor{
    public ScriptDecor(Config conf) {
        super(conf);
    }
    public String getType() {
        return TYPE_SCRIPT;
    }

    static GroovyScriptEngine groovyScriptEngine;

    static {
        try {
            // TODO 从comxconf 里面读取
            groovyScriptEngine = new GroovyScriptEngine("/www/comx-conf/groovy-scripts/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// TODO 暂时只支持 script, lambda 再想办法
    public void doDecorate(Object data, Context context) throws DecorException{
        context.getLogger().error("Decor ScriptDecor: none:" + conf.rawData());
        ArrayList matchedNodes = this.getMatchedNodes(data, context);

        try {
            String scriptName = conf.str("jscript", "");
            String lambda     = conf.str("jlambda", "");
            if (!scriptName.isEmpty()) {
                Class scriptClass = groovyScriptEngine.loadScriptByName(scriptName + ".groovy");
                GroovyObject scriptInstance = (GroovyObject) scriptClass.newInstance();
                for (Object ref: matchedNodes) {
                    Object params = new Object[] {data, context, ref};
                    scriptInstance.invokeMethod("callback", params);
                }
            } else if(!lambda.isEmpty()) {
                for (Object ref: matchedNodes) {
                    HashMap<String, Object> variables = new HashMap<>();
                    variables.put("context", context);
                    variables.put("data", data);
                    variables.put("ref", ref);
                    Binding binding = new Binding(variables);

                    GroovyShell shell = new GroovyShell(binding);
                    shell.evaluate(lambda);
                }
            } else {
                context.getLogger().error("Decor, ScriptDecor script or lambda empty");
            }
            // do nothing
        } catch (Exception ex) {
            // 有以下错误
            //(ResourceException | ScriptException | InstantiationException | IllegalAccessException e1)
            throw new DecorException(ex);
        }
    }



    /**
     * 记录日志需要
     * TODO 和eachdecor 一致， 需要抽象出来
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

}
