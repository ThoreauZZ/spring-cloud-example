package com.gomeplus.comx.schema;

import com.gomeplus.comx.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xue on 12/20/16.
 */
public class TinyTemplate {
    protected String  tpl;
    protected HashMap vars;

    /**
     * constructor;
     * @param tpl
     */
    public TinyTemplate(String tpl) {
        this.tpl = tpl;
    }

    // TODO 影响效率且不好调试，需要变更 java regex 库
    public String render(HashMap vars, Context context) {
        this.vars = vars;
        String ps = "\\{(.*?)\\}";
        Pattern p = Pattern.compile(ps);
        Matcher m = p.matcher(tpl);
        StringBuffer sb = new StringBuffer();
        while (m.find()){
            MatchResult mr = m.toMatchResult();
            String oldTpl = tpl.substring(mr.start(1), mr.end(1));
            String newTpl = replace(oldTpl, context);
            m.appendReplacement(sb, newTpl);
        }
        m.appendTail(sb);
        context.getLogger().trace("Tiny template end:" + sb);
        return sb.toString();
    }

    // 在不使用反射的情况下做到比较不方便
    // TODO 优化
    // 现在的做法是，令vars 以及之后每一层都 imlements Map;
    // fastjson JSONPath 或可以考虑
    public String replace(String matched, Context context) {
        context.getLogger().trace("Tiny template replacing:" + matched);
        String[] varSections = matched.split("\\.");
        Object matchedValue = this.vars;

        for (String key: varSections) {
            if (matchedValue instanceof Map) {
                matchedValue = ((Map)matchedValue).get(key);
            } else {
                context.getLogger().warn("Tiny template failed: matched" + matchedValue + " varSection:" + key);
                return "";
                // TODO 确认行为，记录日志，抛出异常
                //throw new TinyTemplateException("");
            }
        }
        context.getLogger().trace("Tiny template got:" + matchedValue.toString());
        return matchedValue.toString();
    }
}
