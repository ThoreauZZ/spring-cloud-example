package com.erdaoya.springcloud.comx.source.sourcebase;

import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.SourceBizException;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
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


    // return restTemplate.getForEntity("http://config-server/user-service-dev.json", Object.class).getBody();
    // 这个时候 request headers 应当已经被处理好保留字段，只需要封装入client
    //TODO timeout 应当于外部已经被设定
    // rest template 当传递 accept 为 */* 时，返回不能正确解析
    public Object doRequest(RequestMessage requestMessage, Context context) throws SourceException {
        String method                           = requestMessage.getMethod();
        HashMap<String, String> requestheaders  = requestMessage.getHeaderParameters();
        Map<String, Object> data                = requestMessage.getData();
        HttpHeaders headers                     = new HttpHeaders();

        requestheaders.remove("Accept");
        headers.setAll(requestheaders);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity               = (null == data)? new HttpEntity<String>(headers): new HttpEntity<String>(new JSONObject(data).toString(), headers);
        RestTemplate restTemplate               = context.getRestTemplate();
        URI aURI                                = requestMessage.getUrl().getaURI();

        try {
            ResponseEntity<Object> responseEntity = restTemplate.exchange(aURI, HttpMethod.resolve(method.toUpperCase()), entity, Object.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                throw new SourceBizException(responseEntity.getBody().toString(), Integer.parseInt(responseEntity.getStatusCode().toString()));
            }
        } catch (HttpClientErrorException ex) {
            throw new SourceBizException(ex.getStatusText(), Integer.parseInt(ex.getStatusCode().toString()));
        }
    }
}
