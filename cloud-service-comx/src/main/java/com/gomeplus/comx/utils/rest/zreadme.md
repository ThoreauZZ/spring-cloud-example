-   业务无关，可以移动入util
-   requestMessage 初始化传入bootstrap
-   @zhoaqing 设计时， requestMessage 同时作为client请求时的封装（是否保留这个设计?）


-   http client 封装

    rest 使用 client
    提供
        *  apache async client
        *  comsat(fiber) 封装后的 apache async client
        //*  apache client
        *  netty          https://github.com/AsyncHttpClient/async-http-client
   http://allenxwang.blogspot.com/2013/10/netty-httpasyncclient-and-httpclient.html
   测试中 latency 可以接受， throughput 方面 apache_async_client 与 netty 均可以接受且远高于blocking client;
   
   
   
   一些问题: http://www.cnblogs.com/xinsheng/p/5546603.html
   回调函数变成主动调用函数
   长链接问题
   thread-safe 这个问题在 fiber 情况下需要测试和读代码逻辑
