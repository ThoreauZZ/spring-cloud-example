package com.erdaoya.springcloud.comx.schema.datadecor.decors;

import com.erdaoya.springcloud.comx.context.Context;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.HashMap;

/**
 * Created by xue on 2/14/17.
 * 一个lambda 表达式
 * refjsonpath 应当为每一个过滤的 node 做计算
 * 非 refjsonpath 则过一次
 * TODO 脚本出现错误 捕获不处理还是全局中断
 */
public class Precondition {
    public static Boolean execute(String instruction, Object data, Context context) {
        return execute(instruction, data, context, data);
    }
    public static Boolean execute(String instruction, Object data, Context context, Object ref) {
        HashMap<String, Object> variables = new HashMap<String, Object>() {{
            put("context", context);;
            put("data", data);
            put("ref", ref);
        }};
        Binding binding = new Binding(variables);
        GroovyShell shell = new GroovyShell(binding);
        return (Boolean) shell.evaluate(instruction);
    }
}
