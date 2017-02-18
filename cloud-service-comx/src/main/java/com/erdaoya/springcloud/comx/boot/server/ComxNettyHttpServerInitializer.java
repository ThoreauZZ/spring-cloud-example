package com.erdaoya.springcloud.comx.boot.server;

/**
 * Created by xue on 12/26/16.
 */
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class ComxNettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public ComxNettyHttpServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(new ComxNettyServerHandler());
    }
}
