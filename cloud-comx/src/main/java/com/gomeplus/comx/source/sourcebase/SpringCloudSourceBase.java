package com.gomeplus.comx.source.sourcebase;

import com.alibaba.fastjson.JSONObject;
import com.gomeplus.comx.boot.server.ComxSpringCloudApplication;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;
import com.gomeplus.comx.utils.rest.RequestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xue on 2/6/17.
 *
 * TODO 应当是一个插件，暂时这么实现
 * 不配置不应当影响正常服务
 * Spring 在启动时加载？
 * 因为SERVER_ID 不是一个域名，使用通用的方式解析会失败，故自己拼装
 */
public class SpringCloudSourceBase extends AbstractRequestBasedSourceBase{

    static final String FIELD_URL_PREFIX = "urlPrefix";
    public String getUrlPrefix(Context context) throws ConfigException {
        return conf.rstr(FIELD_URL_PREFIX);
    }
    public SpringCloudSourceBase(Config conf){super(conf);}







    public Object doRequest(RequestMessage requestMessage, Context context) {
        String method = requestMessage.getMethod();
        // Prepare header
        HttpHeaders headers = new HttpHeaders();

        RestTemplate restTemplate = context.getRestTemplate();

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Map data = requestMessage.getData();
        //return restTemplate.getForEntity("http://config-server/user-service-dev.json", Object.class).getBody();

        return restTemplate.exchange(requestMessage.getUrl().getaURI(), HttpMethod.GET, entity, Object.class).getBody();


        /*
// set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

// send request and parse result
        ResponseEntity<String> loginResponse = restTemplate
                .exchange(urlString, HttpMethod.POST, entity, String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            JSONObject userJson = new JSONObject(loginResponse.getBody());
        } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            // nono... bad credentials
        }
        */
    }
}
