# spring-cloud-example
---
## 快速起步
### 依赖
* Maven 3
* Java 8
* Docker 1.13.1
* Docker Compose 1.10.0

### 工程说明
工程名就已经说明了用途
* cloud-api-gateway：Zuul实现网关，自定义了返回协议
* cloud-common-exception：自定义了绑定http状态码的异常
* cloud-common-mvn-custom：自定义springMvc处理，比如对Jackson注解无法处理泛型null值
* cloud-service-comx：通过json配置文件，对接口做自动组装

### 克隆并构建

```bash
git clone https://github.com/ThoreauZZ/spring-cloud-example.git
cd spring-cloud-example
mvn clean package -Pdocker
```
打包并生成docker镜像，时间会有点长。


### 启动 
```
docker-compose up -d
```
> Discovery and config 应该在所有服务之前启动，本例通过docker-compose file 2.1版本添加healthcheck方式处理，如果`docker-compose up`后发现其他服没起来，请手动重启，
> `Docker Compose`控制启动顺序见:[https://docs.docker.com/compose/startup-order/](https://docs.docker.com/compose/startup-order/)

其实也不需要控制启动顺序。
1. 实际开发部署时，最好把中间件、框架和服务的编排分开。
2. 如果使用swarm部署，可以设置容器自动重启。
3. 如果是获取配置失败，可以通过设置`spring.cloud.config.retry.*`使服务重启。

### 测试验证服务:
 
 请点击 [验证](1.Test And Verify.md) 


## 架构图
![](images/MicroService.png)


### 高可用
### 注册中心集群
[单击查看](eureka-cluster.md)