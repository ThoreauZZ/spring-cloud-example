package com.erdaoya.springcloud.comx.utils.rest.clients;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.utils.rest.Url;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xue on 12/24/16.
 * TODO 简单编写，待优化
 // TODO 记录日志 或在外层
 */
public class ApacheHttpClient implements HttpClientX {
    public static HttpResponse request(String url, String method, String requestData, HashMap<String, String> headers, Integer timeout) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpUriRequest httpUriRequest = RequestBuilder.create(method).setUri(url).build();
        // set header;
        return httpclient.execute(httpUriRequest);
    }




    public static ResponseMessage request(Url url, String method, String requestData, HashMap<String, String> headers, Integer timeout, String a) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String target = url.getUrl();
        String responseBody = "";

        // 暂时只最简单请求

        try {
            HttpGet httpget = new HttpGet(target);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }

        JSONObject responseBodyJson = JSON.parseObject(responseBody);
        return new ResponseMessage(responseBodyJson.get("data"), responseBodyJson.get("message").toString(), 200);



    }
}
