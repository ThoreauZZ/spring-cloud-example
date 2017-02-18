package com.gomeplus.comx.source.sourcebase;

import com.alibaba.fastjson.JSONObject;
import com.gomeplus.comx.boot.server.ComxSpringCloudApplication;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.source.SourceBizException;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;
import com.gomeplus.comx.utils.rest.RequestMessage;
import com.gomeplus.comx.utils.rest.ResponseMessage;
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







    //return restTemplate.getForEntity("http://config-server/user-service-dev.json", Object.class).getBody();
    // 这个时候 request headers 应当已经被处理好保留字段，只需要封装入client
    public Object doRequest(RequestMessage requestMessage, Context context) throws SourceException{
        String method                           = requestMessage.getMethod();
        HashMap<String, String> requestheaders  = requestMessage.getHeaderParameters();
        Map<String, Object> data                = requestMessage.getData();


        HttpHeaders headers = new HttpHeaders();
        headers.setAll(requestheaders);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity;
        if (null == data) {
            entity = new HttpEntity<String>(headers);
        } else {
            entity = new HttpEntity<String>(new JSONObject(data).toString(), headers);
        }

        RestTemplate restTemplate = context.getRestTemplate();
        //TODO timeout 设置
        //restTemplate.setReadTimeout(requestMessage.getTimeout() * 1000);
        URI aURI = requestMessage.getUrl().getaURI();

        ResponseEntity<Object> responseEntity = restTemplate.exchange(aURI, HttpMethod.resolve(method.toUpperCase()), entity, Object.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return new JSONObject((Map) responseEntity.getBody());
        } else {
            throw new SourceBizException("Source get source url:" + aURI.toString() + ", status code:" + responseEntity.getStatusCode());
        }
        //return restTemplate.exchange(requestMessage.getUrl().getaURI(), HttpMethod.GET, entity, Object.class).getBody();
    }
}
