package com.erdaoya.springcloud.gateway.filter;

import com.erdaoya.springcloud.gateway.model.ResponseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 2017/1/22
 *
 * @author erdaoya
 * @since 1.1
 */
@Slf4j
public class ResponseFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try (InputStream responseDataStream = ctx.getResponseDataStream()) {
            ResponseModel<Object> responseModel = new ResponseModel<>();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            if (responseDataStream == null) {
                ctx.setResponseBody(mapper.writeValueAsString(new ResponseModel()));
                return null;
            }
            final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            int statusCode = ctx.getResponseStatusCode();
            JsonNode jsonNode = mapper.readTree(responseData);


            if (statusCode >= 400 && statusCode < 500) {
                JsonNode message = jsonNode.get("message");
                if (message == null) {
                    responseModel.setMessage("");
                } else {
                    responseModel.setMessage(message.textValue());
                }
                responseModel.setData(new Object());
            } else if (statusCode >= 500) {
                responseModel.setMessage("系统繁忙，请稍后重试");
                responseModel.setData(new Object());
            } else {
                responseModel.setData(jsonNode);
            }
            ctx.setResponseBody(mapper.writeValueAsString(responseModel));

        } catch (IOException e) {
            log.warn("Error reading body", e);
        }
        return null;
    }
}

