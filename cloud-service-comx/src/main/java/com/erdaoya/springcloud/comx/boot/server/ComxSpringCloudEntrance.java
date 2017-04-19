package com.erdaoya.springcloud.comx.boot.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.boot.BootStrap;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;
import com.erdaoya.springcloud.comx.utils.rest.Url;
import com.erdaoya.springcloud.comx.utils.rest.UrlException;
import com.gomeplus.oversea.bs.common.exception.RestfullBaseException;
import com.gomeplus.oversea.bs.common.exception.code3xx.C301Exception;
import com.gomeplus.oversea.bs.common.exception.code3xx.C302Exception;
import com.gomeplus.oversea.bs.common.exception.code4xx.*;
import com.gomeplus.oversea.bs.common.exception.code5xx.C500Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by xue on 2/3/17.
 */
@RestController
public class ComxSpringCloudEntrance {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/**")
    public void boot(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ResponseMessage responseMessage;
        Boolean springSwitch = true;
        try {
            HashMap<String, String> headers = new HashMap<>();
            for (Enumeration headersNames = request.getHeaderNames(); headersNames.hasMoreElements(); ) {
                String headerName = headersNames.nextElement().toString();
                headers.put(headerName, request.getHeader(headerName));
            }
            Url url = new Url(request.getRequestURL() + "?" + request.getQueryString());
            String dataStr = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject data = JSON.parseObject(dataStr);

            if ("1".equals(url.getQuery().get("comxfullreturn"))) springSwitch = false;
            RequestMessage requestMessage = new RequestMessage(url, request.getMethod(), data, headers, 0);
            requestMessage.setRestTemplate(restTemplate);
            responseMessage = BootStrap.start(requestMessage);
        } catch (UrlException ex) {
            String msg = "handle url error:" + ex.getMessage();
            responseMessage = new ResponseMessage(null, msg, 500);
        } catch (IOException ex) {
            String msg = "unserialize post data error:" + ex.getMessage();
            responseMessage = new ResponseMessage(null, msg, 500);
        }

        //printfull(responseMessage, response);
        if (springSwitch)   print(responseMessage, response);
        else                printfull(responseMessage, response);
    }

    public void print(ResponseMessage responseMessage, HttpServletResponse response) throws IOException, RestfullBaseException {
        response.setContentType("text/json;charset=utf-8");
        Integer statusCode = responseMessage.getCode();
        if (statusCode.equals(200)) {
            response.getWriter().println(responseMessage.sendForSpringCloud());
        } else {
            throwExceptions(statusCode, responseMessage.getMessage());
        }
    }

    public void printfull(ResponseMessage responseMessage, HttpServletResponse response) throws IOException{
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(responseMessage.getCode());
        response.getWriter().println(responseMessage.send());
    }


    public void throwExceptions(int statusCode, String message) throws RestfullBaseException {
        switch (statusCode) {
            case 301: throw new C301Exception(message);
            case 302: throw new C302Exception(message);
            case 400: throw new C400Exception(message);
            case 401: throw new C401Exception(message);
            case 403: throw new C403Exception(message);
            case 404: throw new C404Exception(message);
            case 405: throw new C405Exception(message);
            case 406: throw new C406Exception(message);
            case 409: throw new C409Exception(message);
            case 410: throw new C410Exception(message);
            case 415: throw new C415Exception(message);
            case 422: throw new C422Exception(message);
            case 500: throw new C500Exception(message);
            default:throw new C500Exception(message);
        }
    }



















    @RequestMapping(value = "/xue", method = RequestMethod.GET)
    public String hello() {
        JSONObject test = new JSONObject();
        test.put("testxuekey", "testxuevalue");
        return test.toJSONString();
    }


    @RequestMapping(value = "/xuetestservice", method = RequestMethod.GET)
    public String hello2() {
        Object test;
        try {
            test = restTemplate.getForEntity("http://config-server/user-service-dev.json", Object.class).getBody();
            return test.toString();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return "{'testxue':'failed'}";
    }

    @RequestMapping(value = "/xuetestservice0", method = RequestMethod.GET)
    public Object hello4() {
        return restTemplate.getForEntity("http://CONFIG-SERVER/health", Object.class).getBody();
    }

    @RequestMapping(value = "/xuetestservice2", method = RequestMethod.GET)
    public String hello3() {
        return restTemplate.getForEntity("http://comx/xue", Object.class).getBody().toString();
    }
}
