# spring-cloud-example
## Quick Start
### Requirements
The requirements for running this example on your machine are found below.
* Maven 3
* Java 8
* Docker 1.10.3
* Docker Compose 1.9

### clone and build

```bash
git clone https://github.com/ThoreauZZ/spring-cloud-example.git
cd spring-cloud-example
mvn install
```
wait for a period of time。


### start 
```
docker-compose up
```

### test url:
* Eureka dashbord:http://localhost:9001/
* Turbine dashbord:http://10.69.42.86:9010/hystrix
* http://localhost:9000/user-service/dev
* http://localhost:9006/user/user?id=1&token=anything
