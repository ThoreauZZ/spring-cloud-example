# spring-cloud-example
---
English | [中文WIKI](doc/README-CN.md)

## technologies
* spring cloud config 
* Spring Cloud Netflix: eureka zull ribbon feign
* Spring Cloud Bus
* Spring Cloud Sleuth + zipkin
* maven + docker 
* swagger
* rabbitmq mybatis


## Quick Start
### Requirements
The requirements for running this example on your machine are found below.
* Maven 3
* Java 8
* Docker 1.13.1
* Docker Compose 1.10.0

### Clone and Build

```bash
git clone https://github.com/ThoreauZZ/spring-cloud-example.git
cd spring-cloud-example
mvn clean package -Pdocker
```
wait for a period of time。


### Start 
```
docker-compose up -d
```
> Discovery and config shuld be started before all services.
>   Controlling startup order in `Docker Compose`:[https://docs.docker.com/compose/startup-order/](https://docs.docker.com/compose/startup-order/)


> Here user docker-compose file version 2.1 `healthcheck`



## Test And Verify:
 
 Click [here](doc/1.Test And Verify.md) for details


## Architecture
![](doc/images/MicroService.png)
