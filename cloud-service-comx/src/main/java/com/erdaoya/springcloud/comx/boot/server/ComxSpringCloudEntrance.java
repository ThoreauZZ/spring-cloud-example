package com.erdaoya.springcloud.comx.boot.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.boot.BootStrap;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;
import com.erdaoya.springcloud.comx.utils.rest.Url;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.rest.UrlException;
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
        try {
            HashMap<String, String> headers = new HashMap<>();
            for (Enumeration headersNames = request.getHeaderNames(); headersNames.hasMoreElements(); ) {
                String headerName = headersNames.nextElement().toString();
                headers.put(headerName, request.getHeader(headerName));
            }
            Url url = new Url(request.getRequestURL() + "?" + request.getQueryString());
            String dataStr = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject data = JSON.parseObject(dataStr);

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
        print(responseMessage, response);
    }

    public void print(ResponseMessage responseMessage, HttpServletResponse response) throws IOException{
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        //response.getWriter().println(responseMessage.sendForSpringCloud());
        response.getWriter().println(responseMessage.send());
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
