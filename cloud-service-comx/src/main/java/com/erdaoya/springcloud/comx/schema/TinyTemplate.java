package com.erdaoya.springcloud.comx.schema;

import com.erdaoya.springcloud.comx.context.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xue on 12/20/16.
 * 在不使用反射的情况下做到比较不方便
 * TODO 优化
 * 现在的做法是，令vars 以及之后每一层都 imlements Map;
 * fastjson JSONPath 或可以考虑
 * 或者使用 其他正则
 * 下次将此修正为类 静态方法，不需要Object;
 * TODO 另外，是否全部可以URLEncode 还是只有部分需要 URLEncode
 */
public class TinyTemplate {
    public static String ENCODING = "UTF-8";
    protected String  tpl;

    public TinyTemplate(String tpl) {
        this.tpl = tpl;
    }


    // TODO 影响效率且不好调试，需要变更 java regex 库
    public String render(HashMap vars, Context context, Boolean enableUrlEncode) {
        String ps = "\\{(.*?)\\}";
        Pattern p = Pattern.compile(ps);
        Matcher m = p.matcher(tpl);
        StringBuffer sb = new StringBuffer();
        while (m.find()){
            MatchResult mr = m.toMatchResult();
            String oldTpl = tpl.substring(mr.start(1), mr.end(1));
            String newTpl = replace(oldTpl, vars, enableUrlEncode, context);
            m.appendReplacement(sb, newTpl);
        }
        m.appendTail(sb);
        context.getLogger().trace("Tiny template end:" + sb);
        return sb.toString();
    }

    private String replace(String matched, HashMap vars, Boolean enableUrlEncode, Context context) {
        context.getLogger().trace("Tiny template replacing:" + matched);
        String[] varSections = matched.split("\\.");
        Object matchedValue = vars;

        for (String key: varSections) {
            if (null == matchedValue) {
                context.getLogger().warn("Tiny template failed: matched null, varSection:" + key);
                return "";
            }
            if (matchedValue instanceof Map) {
                matchedValue = ((Map)matchedValue).get(key);
            } else {
                context.getLogger().warn("Tiny template failed: matched" + matchedValue + " varSection:" + key);
                return "";
            }
        }
        if (null != matchedValue) {
            context.getLogger().trace("Tiny template got:" + matchedValue.toString());
            try {
                return enableUrlEncode ? URLEncoder.encode(matchedValue.toString(), ENCODING) : matchedValue.toString();
            } catch (UnsupportedEncodingException ex) {
                context.getLogger().warn("Tiny template UnsupportedEncodingException:"+ ex.getMessage()+ " got:" + matchedValue.toString());
                return "";
            }
        }
        return "";
    }
}
