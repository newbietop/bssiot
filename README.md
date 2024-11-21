# 

## Model
www.msaez.io/#/188553381/storming/bssiot-prj

## 예제
### BSS-IoT(빌링시스템)

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.

## Table of contents

목차 정리 필요

## 클라우드 아키텍쳐 설계


## 서비스 시나리오

기능적 요구사항
1. 고객이 외부 웹 사이트를 통해 단말기 회선의 오더를 요청한다
    1. 오더에는 개통,정지,정지복구,해지,해지복구가 있다.
    1. 오더 상태가 변경될때마다 고객에서 sms를 전송한다.
    1. 고객은 회선 상태를 조회 및 변경할 수 있다.
    1. 고객이 신규 개통하면 레이터팀과 빌링팀에 새로운 정보를 생성한다. 
2. 개통이 되면 과금 파일이 들어오면 해당 고객정보를 찾아 레이팅을 하여 데이터 사용량을 누적한다.
3. 사용량이 누적되면 월 말마다 과금팀이 AU 마감을 통해 Moved되면 해당 누적 사용량이 빌링팀으로 전달된다.
    1. 사용량 누적된 데이터가 전달되면 빌링팀은 요금 계산을 진행한다. 
4. 계산이 끝난 요금들은 확정된 요금들이며 고객들에게 청구서를 만들어 제공한다.
5. 청구서까지 제공되면 정산팀에 데이터를 전달하여 월 총 사용량 및 총 요금을 정산한다.
6. 해당 문서는 고객 및 사업부서에 메일로 전달한다.


## 이벤트 스토밍 

완성된 모형
![image](https://github.com/newbietop/bssiot/blob/main/%EC%9D%B4%EB%B2%A4%ED%8A%B8%20%EC%8A%A4%ED%86%A0%EB%B0%8D1.PNG)

모형 검증
1. 고객이 개통을 하면 rate팀과 billing팀에도 신규 info정보가 생성되는가?(ok)
2. 고객이 직접 조회 및 회선 상태 변경이 가능한가?(ok)
3. 해당 apncd가 아닌 다른 apncd일시 과금이 누적되지 않는가?(ok)
4. au마감을 진행하였을때 요금계산이 정상으로 진행되는가?(ok)
5. 요금계산이 정상으로 진행될때 청구서 생성이 정상으로 진행되는가?(ok)
6. 청구서 생성이 완료되면 정산팀으로 해당 데이터를 정상 전송하는가?(ok)


## MSA 개발 또는 역량 관리
### 분산 트랜잭션
OrderCreated되면 각각의 서비스인  rate_info와 bill_info가 생성되야한다.

하기는  Order aggregate의 모습이다. 해당 데이터가 GateWay를 통해 customer 서비스에서 생성되면 rate의 wheneverOrderCreated_CreateRaterInfo를 통해 rate aggregate가 신규로 생성된다.
동일하게 billing의 wheneverOrderCreated_CreateChargeInfo를 통해서도 신규로 생성된다.
```
package bssiot.domain;

import bssiot.CustomerApplication;
import bssiot.domain.OrderChaged;
import bssiot.domain.OrderCreated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Customer_table")
@Data
//<<< DDD / Aggregate Root
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;

    private String productCd;

    private String svcContStatus;

    private Integer svcContNo;

    private String email;

    private String address;

    private String productNm;

    private Integer productTarif;

    private Integer chargeAccount;

    private String apnCd;

    @PostPersist
    public void onPostPersist() {
        OrderCreated orderCreated = new OrderCreated(this);
        orderCreated.publishAfterCommit();

        OrderChaged orderChaged = new OrderChaged(this);
        orderChaged.publishAfterCommit();
    }

    public static CustomerRepository repository() {
        CustomerRepository customerRepository = CustomerApplication.applicationContext.getBean(
            CustomerRepository.class
        );
        return customerRepository;
    }
}
//>>> DDD / Aggregate Root

```
![image](https://github.com/newbietop/bssiot/blob/main/saga%201.PNG) 
-> gateway를 통해 신규 고객 생성

![image](https://github.com/newbietop/bssiot/blob/main/saga2.PNG) 
-> rater서비스에 신규 생성된 정보 확인

![image](https://github.com/newbietop/bssiot/blob/main/saga3.PNG) 
-> billing서비스에 신규 생성된 정보 확인

### 보상처리-compensation

고객팀에 신규 회선 가입자가 해지를 요청한 상태에서 한번 더 해지를 요청하면 rater의 useYn을 보고 회선의 상태를 이미 해지됨 상태로 변경함

1.신규 회선 가입자
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC1.PNG)

2.고객 및 rater팀 확인
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC2.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC3.PNG)

3.첫 해지 요청
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC4.PNG)

4.두번째 해지 요청시 회선 상태 확인
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC5.PNG)

5.kafaka data 확인 두번째 해지시 raterUnused 이벤트 발생
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC6.PNG)

### 단일 진입점

gateway 단일 진입점을 통해 전체 서비스 메서드 조회 가능
gateway port: 8088

gateway의 application.yml
```
spring:
  profiles: default
  cloud:
    gateway:
#<<< API Gateway / Routes
      routes:
        - id: customer
          uri: http://localhost:8082
          predicates:
            - Path=/customers/**, 
        - id: rate
          uri: http://localhost:8083
          predicates:
            - Path=/raters/**, 
        - id: billing
          uri: http://localhost:8084
          predicates:
            - Path=/billings/**, 
        - id: settlement
          uri: http://localhost:8085
          predicates:
            - Path=/settlements/**, 
        - id: my page
          uri: http://localhost:8086
          predicates:
            - Path=, 
        - id: frontend
          uri: http://localhost:8080
          predicates:
            - Path=/**
```

1.공통 요청시 고객팀, 빌링팀, 과금팀 데이터 생성

![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC2.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/%EB%B3%B4%EC%83%81%EC%B2%98%EB%A6%AC3.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/gateway.PNG)

2.kafka 이벤트를 통해 각각 접근 가능
![image](https://github.com/newbietop/bssiot/blob/main/kafka.PNG)

### 분산 데이터 프로젝션-CQRS



## 클라우드 배포 - container 운영
test







