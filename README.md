# spring-cloud-example
## Quick Start
### 1. Requirements
The requirements for running this example on your machine are found below.
* Maven 3
* Java 8
* Docker 1.10.3
* Docker Compose 1.9

### 2. clone 

```bash
git clone https://github.com/ThoreauZZ/spring-cloud-example.git
```

### 3. build

```bash
cd spring-cloud-example
mvn install
```
wait for a period of time。


### 4. start 
```
docker-compose up
```

### 5. test url:
* http://localhost:9000/user-service/dev
* http://10.69.42.86:9006/user/user?id=1&accessToken=anything
