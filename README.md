# Spring_Cloud

./gradlew bootRun --args='--server.port={server.port}'

# 0장

### 클라우드 네이티브 아키텍쳐
- 확장 가능한 아키텍처
- 탄력적 아키텍처
- 장애격리

  
### 클라우드 네이티브 애플리케이션 (마이크로서비스, CI/CD, DevOps, Container)

### SOA 와 MSA의 차이
SOA - 공통 자원을 재사용하여 비용 절감
MSA - 서비스 간의 결합도 낮추어 변화에 능동적으로 대응

### MSA
![image](https://github.com/jihaneol/Spring_Cloud/assets/104291422/c6ad42cf-f124-460a-91ca-bed534e1f19f)

- 대용량 데이터 처리, 실시간, 고성능, 고가용성이 필요한 경우, 또는 저장된 이벤트를 기반으로 로그를 추적하고 재처리 하는게 필요한 경우 kafka를 쓰자.
- 복잡한 라우팅을 유연하게 처리해야하고, 정확한 요청-응답이 필요한 Application을 쓸 때 또는 트래픽은 작지만 장시간 실행되고 안정적인 백그라운드 작업이 필요한 경우 RabbitMQ를 쓰자.
- 이벤트 데이터를 DB에 저장하기 때문에 굳이 미들웨어에 이벤트를 저장할 필요가 없는 경우, consumer에게 굳이 꼭 알람이 도착해야한다는 보장 없이 알람처럼 push 보내는것만 중요하다면 유지보수가 편한 Redis를 사용하자.

## 기반 기술

1. Gateway - nginx, zuul
2. Mesh/Meta service -  라우터, 로드 밸런싱 
3. Runtime - docker, kubernetes
4. Frameworks - springboot
5. Automation - jenkins, gradle, maven, npm  CI/CD
6. Backing Service - kafka, redis, ec2, MQ  저장소
7. Telemetry - 현재상태 리소스 모니터링

# 1장

## Spring Cloud

### Spring Cloud Netflix Eureka (spring cloud discovery)

여러개의 서비스 인스터스를 등록, 검색 해주며 API Gateway(Load balancer)에 알려주는 역할 

`@EnableEurekaServer로 등록`

유레카 디스커버리 - 유레카 서버

### 인스턴스

유레카 디스커버리 - 클라이언트

`@EnableDiscoveryClient`

# user-service

server.port = 0  랜덤포트 

문제 - 유레카서버에서는 정적포트만 인식하기 때문에 서버 하나만 표시

해결  

`eureka:`  

`instance:`    

`instance-id: ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}}`

# 2장

### 스프링 클라우드 게이트웨이

Netflix Zuul - gateway - 2.4 유지 더 이상 지원 안한다. → 대체 스프릥 클라우드 게이트웨이

zuul 서버 `@EnableZuulProxy`

spring.cloud.gateway (비동기 처리 가능)

`id: first-service`  

`uri: http://localhost:8081/`  

`predicates:`    

`- Path=/first-service/**`

http://localhost:8081/first-service/ 로 들어가진다.

![image](https://github.com/jihaneol/Spring_Cloud/assets/104291422/a09351ea-ede7-46da-86a1-7dead21d37a1)


coustom filter 적용

AbstractGatewayFilterFactory<CustomFilter.config> 상속 

ServerHttpRequest/Response 사용, Netty 비동기 방식이라서.

apply 오버라이드

global filter

yaml에 default-filters: 추가 , args 추가 가능

개별 필터와 동일하다.

글로벌 시작 - 개벌 시작 - 개별 종료 - 글로벌 종료

logging filter

우선순위 적용 할 수 있다. 

global - custom - logger - custom - gobal 순

![image](https://github.com/jihaneol/Spring_Cloud/assets/104291422/a47859b0-0d9b-4b57-8ca2-175ef431c94c)


### Eureka  연동

`uri: lb://MY-SECOND-SERVICE` 유레카 서버에 등록된 이름으로 설정

오류..

[https://velog.io/@wlsh44/Spring-데이터베이스-초기화](https://velog.io/@wlsh44/Spring-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%B4%88%EA%B8%B0%ED%99%94) 

db 초기화 ..

application.yml → application-name.yml(ecomerce.yml) → application-name-<profile>.yml(ecomerce-dev)

config server 변경시 다시 갖고 오는 방법

1. 서버 재기동 : 별루
2. Actuator refresh : 재부팅 안해도 된다.
3. Spring cloud bus 사용 : 2번보다 효율적
- bootstrap.yml 관련 내용
    
    Spring Boot 2.4 이후부터는 bootstrap.yml(bootstrap.properties)에 의한 context 초기화 작업이 지원되지 않습니다. bootstrap.yml파일을 사용하기 위해서는 spring.cloud.bootstrap.enabled=true, 또는 말씀하신 내용 처럼 spring-cloud-starter-bootstrap을 사용하시고, boostrap.yml 파일을 사용하지 않기 위해서는 spring.cloud.import=optionall:configserver:http://localhost:8888 과 같이 설정해서 사용하실 수 있습니다.
