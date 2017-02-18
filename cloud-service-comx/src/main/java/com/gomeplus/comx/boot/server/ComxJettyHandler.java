package com.gomeplus.comx.boot.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gomeplus.comx.boot.BootStrap;
import com.gomeplus.comx.utils.rest.RequestMessage;
import com.gomeplus.comx.utils.rest.ResponseMessage;
import com.gomeplus.comx.utils.rest.Url;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by xue on 12/13/16.
 */

class ComxJettyHandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComxJettyServer.class);

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // build request and boot
        HashMap<String,String> headers  = new HashMap<>();
        for (Enumeration headersNames = request.getHeaderNames(); headersNames.hasMoreElements();) {
            String headerName = headersNames.nextElement().toString();
            headers.put(headerName, request.getHeader(headerName));
        }
        try {
            Url url = new Url(request.getRequestURL() + "?" + request.getQueryString());
            String dataStr = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject data = JSON.parseObject(dataStr);


            RequestMessage requestMessage = new RequestMessage(url, request.getMethod(), data, headers, 0);
            ResponseMessage responseMessage = BootStrap.start(requestMessage);

            // 将服务器处理后的结果返回给调用URL的客户端
            print(baseRequest, response, responseMessage);
            //ResponseMessage responseMessage = new ResponseMessage(null, "", "200");
            //print(baseRequest, response, responseMessage);
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    /**
     * <pre>
     * @param baseRequest
     * @param response
     * @param responseMessage 需要返回给客户的结果
     * @throws IOException
     * 将结果 result 返回给客户
     * </pre>
     */
    private void print(Request baseRequest, HttpServletResponse response, ResponseMessage responseMessage) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println(responseMessage.send());
    }
}

