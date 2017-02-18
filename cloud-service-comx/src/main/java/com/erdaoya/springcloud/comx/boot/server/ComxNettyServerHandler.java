package com.erdaoya.springcloud.comx.boot.server;

/**
 * Created by xue on 12/25/16.
 */


import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.boot.BootStrap;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;
import com.erdaoya.springcloud.comx.utils.rest.Url;
import com.erdaoya.springcloud.comx.utils.rest.UrlException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class ComxNettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UrlException {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            if (HttpUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }

            String url = req.uri();
            String method = req.method().name();
            HttpHeaders httpHeaders = req.headers();
            List<Map.Entry<String, String>> headerNames = httpHeaders.entries();
            HashMap<String, String> headerParameters = new HashMap<>();
            for (Map.Entry<String, String> entry: headerNames) {
                headerParameters.put(entry.getKey(), entry.getValue());
            }
            // TODO 优化 读 body
            JSONObject data = new JSONObject();
            url = "http://127.0.0.1:8889" + url;
            Url rurl = new Url(url);
            //RequestMessage requestMessage = new RequestMessage();
            //requestMessage.setMethod(method);
            //requestMessage.setUrl(rurl);
            //requestMessage.setData(data);
            //requestMessage.setHeaderParameters(headerParameters);

            RequestMessage requestMessage = new RequestMessage(rurl, method, data, headerParameters, 0);



            ResponseMessage responseMessage = BootStrap.start(requestMessage);
            responseMessage.setJsonp(requestMessage.getJsonp());








            boolean keepAlive = HttpUtil.isKeepAlive(req);
            //FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseMessage.send().getBytes()));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

