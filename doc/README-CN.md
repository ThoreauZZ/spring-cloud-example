# spring-cloud-example
---
## 快速起步
### 依赖
* Maven 3
* Java 8
* Docker 1.10.3
* Docker Compose 1.9

### 克隆并构建

```bash
git clone https://github.com/ThoreauZZ/spring-cloud-example.git
cd spring-cloud-example
mvn clean package -Pdocker
```
打包并生成docker镜像，时间会有点长。


### 启动 
```
docker-compose up
```
> Discovery and config 应该在所有服务之前启动，本例通过docker-compose file 2.1版本添加healthcheck方式处理，如果`docker-compose up`后发现其他服没起来，请手动重启，
> `Docker Compose`控制启动顺序见:[https://docs.docker.com/compose/startup-order/](https://docs.docker.com/compose/startup-order/)

### 测试验证服务:
 
 请点击 [验证](1.Test And Verify.md) 


## 架构图
![](images/MicroService.png)
