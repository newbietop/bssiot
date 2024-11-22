# 

## Model
www.msaez.io/#/188553381/storming/bssiot-prj

## 예제
### BSS-IoT(빌링시스템)

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.

## 클라우드 아키텍쳐 설계

![image](https://github.com/newbietop/bssiot/blob/main/%EA%B5%AC%EC%84%B1%EB%8F%84.PNG)

## 서비스 시나리오

기능적 요구사항
1. 고객이 외부 웹 사이트를 통해 단말기 회선의 오더를 요청한다
    1. 오더에는 개통,정지,정지복구,해지,해지복구가 있다.(간편하게 해지만 되도록 설정)
    1. 오더 상태가 변경될때마다 고객에서 sms를 전송한다.
    1. 고객은 회선 상태를 조회 및 변경할 수 있다.
    1. 고객이 신규 개통하면 레이터팀과 빌링팀에 새로운 정보를 생성한다.
    1. 이미 해지된 고객은 해지 요청을 하게되면 rater팀에 사용유무 확인 후 이미 해지됨으로 상태 변경
2. 개통이 되면 과금 파일이 들어오면 해당 고객정보를 찾아 레이팅을 하여 데이터 사용량을 누적한다.
3. 사용량이 누적되면 월 말마다 과금팀이 AU 마감을 통해 Moved되면 해당 누적 사용량이 빌링팀으로 전달된다.
    1. 사용량 누적된 데이터가 전달되면 빌링팀은 요금 계산을 진행한다. 
4. 계산이 끝난 요금들은 확정된 요금들이며 고객들에게 청구서를 만들어 제공한다.
5. 청구서까지 제공되면 정산팀에 데이터를 전달하여 월 총 사용량 및 총 요금을 정산한다.
6. 해당 문서는 고객 및 사업부서에 메일로 전달한다.


## 이벤트 스토밍 

완성된 모형
![image](https://github.com/newbietop/bssiot/blob/main/%EC%9D%B4%EB%B2%A4%ED%8A%B8%20%EC%8A%A4%ED%86%A0%EB%B0%8D3.PNG)

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

cqrs 모델링(실제 소스 개발 및 테스트x)
![image](https://github.com/newbietop/bssiot/blob/main/%EC%9D%B4%EB%B2%A4%ED%8A%B8%20%EC%8A%A4%ED%86%A0%EB%B0%8D2.PNG)

![image](https://github.com/newbietop/bssiot/blob/main/cqrs.PNG)

## 클라우드 배포 - container 운영

### 도커라이징
docker 로그인 및 docker image push
![image](https://github.com/newbietop/bssiot/blob/main/docker%20%EB%9D%BC%EC%9D%B4%EC%A7%95.PNG)

### 클러스터에 배포
helm 설치
![image](https://github.com/newbietop/bssiot/blob/main/helm%EC%84%A4%EC%B9%98%20%EB%B0%8F%20%ED%99%95%EC%9D%B8.PNG)

### 서비스 기동
gateway, rate, customer, billing, settlement 기동

gateway통한 배포
![image](https://github.com/newbietop/bssiot/blob/main/%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C%20%EC%95%B1%20%EB%B0%B0%ED%8F%AC-gateway.PNG)

각 설정 확인

customer
![image](https://github.com/newbietop/bssiot/blob/main/%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C%20%EC%95%B1%20%EB%B0%B0%ED%8F%AC-customer.PNG)

billing
![image](https://github.com/newbietop/bssiot/blob/main/%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C%20%EC%95%B1%20%EB%B0%B0%ED%8F%AC-billing.PNG)

rater
![image](https://github.com/newbietop/bssiot/blob/main/%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C%20%EC%95%B1%20%EB%B0%B0%ED%8F%AC-raters.PNG)

### CI-CD 설정을 위한 jenkins 세팅

젠킨스 설정을 위한 VM 생성
![image](https://github.com/newbietop/bssiot/blob/main/vm%20%EC%84%A4%EC%A0%95.PNG)
vm 설정을 통해 계정 생성

vm에 젠킨스 설치
![image](https://github.com/newbietop/bssiot/blob/main/%EC%A0%A0%ED%82%A8%EC%8A%A4%20%EC%84%A4%EC%B9%98.PNG)

젠킨스 ip로 접속하여 로그인
![image](https://github.com/newbietop/bssiot/blob/main/%EC%A0%A0%ED%82%A8%EC%8A%A4%20%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4.PNG)

젠킨스 파일 설정을 통한 CI-CD 구현
1.jenkinsFile
```
pipeline {
    agent any

    environment {
        REGISTRY = 'user04.azurecr.io'
        SERVICES = 'customer' // fix your microservices
        AKS_CLUSTER = 'user04-aks'
        RESOURCE_GROUP = 'user04-rsrcgrp'
        AKS_NAMESPACE = 'default'
        AZURE_CREDENTIALS_ID = 'Azure-Cred'
        TENANT_ID = '29d166ad-94ec-45cb-9f65-561c038e1c7a' 
    }

    stages {
        stage('Clone Repository') {
            steps {
                checkout scm
            }
        }

        stage('Build and Deploy Services') {
            steps {
                script {
                    def services = SERVICES.tokenize(',') // Use tokenize to split the string into a list
                    for (int i = 0; i < services.size(); i++) {
                        def service = services[i] // Define service as a def to ensure serialization
                        dir(service) {
                            stage("Maven Build - ${service}") {
                                withMaven(maven: 'Maven') {
                                    sh 'mvn package -DskipTests'
                                }
                            }

                            stage("Docker Build - ${service}") {
                                def image = docker.build("${REGISTRY}/${service}:v${env.BUILD_NUMBER}")
                            }

                            stage('Azure Login') {
                                withCredentials([usernamePassword(credentialsId: env.AZURE_CREDENTIALS_ID, usernameVariable: 'AZURE_CLIENT_ID', passwordVariable: 'AZURE_CLIENT_SECRET')]) {
                                    sh 'az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET --tenant ${TENANT_ID}'
                                }
                            }

                            stage("Push to ACR - ${service}") {
                                sh "az acr login --name ${REGISTRY.split('\\.')[0]}"
                                sh "docker push ${REGISTRY}/${service}:v${env.BUILD_NUMBER}"
                            }

                            stage("Deploy to AKS - ${service}") {
                                
                                sh "az aks get-credentials --resource-group ${RESOURCE_GROUP} --name ${AKS_CLUSTER}"

                                sh 'pwd'
                                
                                sh """
                                sed 's/latest/v${env.BUILD_ID}/g' kubernetes/deployment.yaml > output.yaml
                                cat output.yaml
                                kubectl apply -f output.yaml
                                kubectl apply -f kubernetes/service.yaml
                                rm output.yaml
                                """
                            }
                        }
                    }
                }
            }
        }

        stage('CleanUp Images') {
            steps {
                script {
                    def services = SERVICES.tokenize(',') 
                    for (int i = 0; i < services.size(); i++) {
                        def service = services[i] 
                        sh "docker rmi ${REGISTRY}/${service}:v${env.BUILD_NUMBER}"
                    }
                }
            }
        }
    }
}
```

2.customer쪽 정합성을 위한 image url 설정
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: customer
  labels:
    app: customer
spec:
  replicas: 1
  selector:
    matchLabels:
      app: customer
  template:
    metadata:
      labels:
        app: customer
    spec:
      containers:
        - name: customer
          image: "user04.azurecr.io/customer:latest"
```

3.배포 전  pod 상태 확인
![image](https://github.com/newbietop/bssiot/blob/main/%EC%9E%90%EB%8F%99%EB%B0%B0%ED%8F%AC%EC%A0%84.PNG)

4.배포 후 jenkins 자동 빌드 확인 및 자동 배포 확인
![image](https://github.com/newbietop/bssiot/blob/main/%EC%9E%90%EB%8F%99%EB%B9%8C%EB%93%9C%20%ED%99%95%EC%9D%B8.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/%EC%9E%90%EB%8F%99%EB%B0%B0%ED%8F%AC%ED%9B%84.PNG)

### istio설정

istio 설치 및 세팅
![image](https://github.com/newbietop/bssiot/blob/main/KakaoTalk_20241122_010812386_03.png)

bssiot 서비스 모니터링을 위한 namespace설정 및 pod 기동
![image](https://github.com/newbietop/bssiot/blob/main/KakaoTalk_20241122_010812386_05.png)

그러나 kafka까지 올릴려 했으나 커넥션이 잘 안돼 pod기동 실패
![image](https://github.com/newbietop/bssiot/blob/main/namespace.PNG)

실제 kiali와 JaegerUI 화면
![image](https://github.com/newbietop/bssiot/blob/main/bssiot%20kiali%ED%99%94%EB%A9%B4.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/KakaoTalk_20241122_010812386.png)

### 모니터링을 위한 프로메테우스 그라파나 설치
프로메테우스 - 실제 pod들이 계속 안떠서 테스트 실패
![image](https://github.com/newbietop/bssiot/blob/main/%ED%94%84%EB%A1%9C%EB%A9%94%ED%85%8C%EC%9A%B0%EC%8A%A4%201.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/%ED%94%84%EB%A1%9C%EB%A9%94%ED%85%8C%EC%9A%B0%EC%8A%A4%202.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/%ED%94%84%EB%A1%9C%EB%A9%94%ED%85%8C%EC%9A%B0%EC%8A%A4%20%EC%84%A4%EC%B9%98.PNG)
![image](https://github.com/newbietop/bssiot/blob/main/%ED%94%84%EB%A1%9C%EB%A9%94%ED%85%8C%EC%9A%B0%EC%8A%A4%20%ED%99%94%EB%A9%B4.PNG)

그라파나 화면
![image](https://github.com/newbietop/bssiot/blob/main/%EA%B7%B8%EB%9D%BC%ED%8C%8C%EB%82%98%20%EA%B8%B0%EB%8F%99.PNG)





