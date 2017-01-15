# spring-cloud-example
## Quick Start
### Requirements
The requirements for running this example on your machine are found below.
* Maven 3
* Java 8
* Docker 1.10.3
* Docker Compose 1.9

### Clone and Build

```bash
git clone https://github.com/ThoreauZZ/spring-cloud-example.git
cd spring-cloud-example
mvn install
```
wait for a period of time。


### Start 
```
docker-compose up
```

### Test And Verify:
#### 1. Config : [http://localhost:9000/user-service/dev](http://localhost:9000/user-service/dev)
#### 2. Eureka-dashbord : [http://localhost:9001/](http://localhost:9001/)
![](doc/images/eureka-admin.png)
curl -s -H "Accept:application/json" http://localhost:9001/eureka/apps | jq '.applications.application[] | {service: .name, ip: .instance[].ipAddr, prot: .instance[].port."$"}'

```
{
  "service": "TURBINE-DASHBORD",
  "ip": "172.18.0.10",
  "prot": 9010
}
{
  "service": "TRADE-SERVICE",
  "ip": "172.18.0.8",
  "prot": 9007
}
{
  "service": "API-GATEWAY",
  "ip": "172.18.0.11",
  "prot": 9006
}
{
  "service": "SERVICE-EXT",
  "ip": "172.18.0.9",
  "prot": 9005
}
{
  "service": "ZIPKIN-UI",
  "ip": "172.18.0.6",
  "prot": 9012
}
{
  "service": "CONFIG-SERVER",
  "ip": "172.18.0.5",
  "prot": 9000
}
{
  "service": "USER-SERVICE",
  "ip": "172.18.0.7",
  "prot": 9002
}
```
#### 3. Turbine-dashbord : [http://localhost:9010/hystrix](http://localhost:9010/hystrix)

![](doc/images/Hystrix Dashboard.png)
Paste url[http://localhost:9010/turbine.stream](http://localhost:9010/turbine.stream) in Hystrix Dashboard, and moniter stream.
![](doc/images/Hystrix Stream turbine.png)


#### 4. Zipkin UI

   http://localhost:9012   
   ![](doc/images/zipkin01.png)
   ![](doc/images/zipkin02.png)

#### 5. get token and test api

With implicit grant, sikp code grant. Use the following URL in a web browser

http://localhost:9006/oauth/authorize?client_id=client&secret_id=secret&response_type=token&redirect_uri=http://localhost

Login(user/password),and redirect to a URL like:
```
http://localhost:9006/#
    access_token=92fd0e5f-4dba-44cd-b199-7fe1aae5a300
    &token_type=bearer
    &expires_in=43180
    &scope=app

```
`TOKEN = 92fd0e5f-4dba-44cd-b199-7fe1aae5a300`


$ curl -s http://localhost:9006/ext/user\?id\=1\&token\=sy\&access_token\=92fd0e5f-4dba-44cd-b199-7fe1aae5a300 |jq .
```json
{
  "id": 1,
  "loginName": "erdaoya",
  "nickName": "erdaoya",
  "password": "1234",
  "mobile": "12345678909",
  "email": "xx@gmail.com",
  "gender": 0,
  "registerTime": 2017,
}
```

curl -s http://localhost:9006/user/persionalInfo\?id\=1\&token\=sy\&access_token\=92fd0e5f-4dba-44cd-b199-7fe1aae5a300 |jq .
```json
{
  "id": 1,
  "loginName": "erdaoya",
  "nickName": "erdaoya",
  "password": "1234",
  "mobile": "12345678909",
  "email": "xx@gmail.com",
  "gender": 0,
  "registerTime": 2017,
  "updateTime": null
}
```

## Architecture
![](doc/images/MicroService.png)