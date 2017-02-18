package com.erdaoya.springcloud.comx.boot.server;

/**
 * Created by xue on 12/13/16.
 */

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComxJettyServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComxJettyServer.class);

    /**
     * <pre>
     * @param args
     * </pre>
     */
    public static void main(String[] args) {
        try {
            // 进行服务器配置
            Server server = new Server(8888);
            LOGGER.info("server start.");
            ContextHandler context = new ContextHandler();
            // 设置搜索的URL地址
            context.setContextPath("/");
            context.setResourceBase(".");
            context.setClassLoader(Thread.currentThread().getContextClassLoader());
            server.setHandler(context);
            context.setHandler(new ComxJettyHandler());
            // 启动服务器
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
