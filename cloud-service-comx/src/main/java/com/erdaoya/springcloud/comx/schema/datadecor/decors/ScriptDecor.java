package com.erdaoya.springcloud.comx.schema.datadecor.decors;

import com.erdaoya.springcloud.comx.schema.datadecor.DecorException;
import com.erdaoya.springcloud.comx.boot.ComxConfLoader;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.utils.config.Config;
import groovy.lang.Binding;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * http://wiki.jikexueyuan.com/project/groovy-introduction/integrating-groovy-applications.html
 * Created by xue on 1/17/17.
 * TODO 勉强能用 需要测试用例
 */
@Slf4j
public class ScriptDecor extends AbstractDecor implements RefJsonPath{
    public ScriptDecor(Config conf) {
        super(conf);
    }
    public String getType() {
        return AbstractDecor.TYPE_SCRIPT;
    }

    static GroovyScriptEngine groovyScriptEngine;

    static {
        try {
            Properties prop     = new Properties();
            InputStream in      = ComxConfLoader.class.getClassLoader().getResourceAsStream("comx.properties");
            prop.load(in);
            String groovyHome   = prop.getProperty("comx_home") + "/groovy-scripts/";
            groovyScriptEngine  = new GroovyScriptEngine(groovyHome);
        } catch (IOException e) {
            log.error("fail to load properties!", e);
            throw new RuntimeException(e);
        }
    }

    public void doDecorate(Object data, Context context) throws DecorException {
        context.getLogger().error("Decor ScriptDecor: init:" + conf.rawData());
        List matchedNodes = getMatchedNodes(conf, data, context);

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
                context.getLogger().error("Decor, ScriptDecor jscript or jlambda empty");
            }
            // do nothing
        } catch (Exception ex) {
            // 有以下错误
            //(ResourceException | ScriptException | InstantiationException | IllegalAccessException e1)
            throw new DecorException(ex);
        }
    }
}
