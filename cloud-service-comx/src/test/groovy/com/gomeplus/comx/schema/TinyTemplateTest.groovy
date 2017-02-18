package com.gomeplus.comx.schema

import com.alibaba.fastjson.JSONObject
import com.gomeplus.comx.context.Context
import com.gomeplus.comx.utils.config.ConfigException
import com.gomeplus.comx.utils.config.Loader
import com.gomeplus.comx.utils.log.ComxLogger
import com.gomeplus.comx.utils.rest.RequestMessage
import com.gomeplus.comx.utils.rest.Url

/**
 * Created by xue on 12/20/16.
 */
import org.junit.Test

/**
 * Created by xue on 12/8/16.
 */
class TinyTemplateTest extends GroovyTestCase {

    @Test
    public void testTinyTemplate() {
        String str = "/v2/friendship/application?userId={request.url.query.userId}&memo={request.data.memo}";
        TinyTemplate tinyTemplate = new TinyTemplate(str);
        String urlStr = "http://127.0.0.1:80/testpath/?userId=test&test2=value2#tt";
        Url url = new Url(urlStr);
        HashMap headers = new HashMap();
        JSONObject data = new JSONObject();
        data.put("memo", "ttest");

        RequestMessage requestMessage = new RequestMessage(url, "get", data, headers, 0);

        Context context = new Context();
        context.setLogger(new ComxLogger());
        HashMap vars = new HashMap();
        vars.put("request", requestMessage);

        String newtpl = tinyTemplate.render(vars, context);
        assert newtpl.equals("/v2/friendship/application?userId=test&memo=ttest");
    }
}
