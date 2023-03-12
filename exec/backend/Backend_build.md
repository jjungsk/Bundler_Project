# 백엔드 프로젝트 빌드

## Contents
---
- [백엔드 프로젝트 빌드](#백엔드-프로젝트-빌드)
  - [Contents](#contents)
  - [Summary](#summary)
  - [Dependencies](#dependencies)
  - [DB 세팅](#db-세팅)
    - [1. DBMS version.](#1-dbms-version)
    - [2. Install MariaDB](#2-install-mariadb)
    - [3. Initialize scheme](#3-initialize-scheme)
  - [백엔드 빌드 방법](#백엔드-빌드-방법)
    - [Common](#common)
    - [CLI](#cli)
    - [IntelliJ IDEA](#intellij-idea)

## Summary
---
DB 세팅 후 백엔드 빌드 방법에 따라 진행 

백엔드 빌드 방법은 CLI와 IDEA 에서 빌드하는 방법 있음

DB 세팅 , Common은 공통으로 진행 후 선택한 방법에 따라 진행

## Dependencies
---
* IDEA : IntelliJ IDEA 2022.3.1 (Ultimate Edition)
* SDK : Azul Zulu Version 17.0.5 
* Gradle : Gradle 7.6
* JVM: 17.0.5 (Azul Systems, Inc. 17.0.5+8-LTS)

## DB 세팅
---

### 1. DBMS version.

    MariaDB 10.10.2

### 2. Install MariaDB

* With Windows

    [Installing MariaDB MSI Packages on Windows
    ](https://mariadb.com/kb/en/installing-mariadb-msi-packages-on-windows/)

* With Docker CLI (Docker Daemon needed)

        docker run -dp 3306:3306 \n
        --env MARIADB_ROOT_PASSWORD=my-secret-pw \n
        --env MARIADB_DATABASE=mydata \n
        --env MARIADB_USER=myuser \n
        --env MARIADB_PASSWORD=mypassword \n
        mariadb:latest


### 3. Initialize scheme

Maria DB 설치 후 다음 sql 실행

- [scheme_ddl.sql](scheme_ddl.sql)


## 백엔드 빌드 방법
---
1. With CLI
   
    [Common](#common) > [Build with CLI](#cli)

1. With IntelliJ IDEA
   
    [Common](#common) > [With IntelliJ](#intellij-idea)

### Common

1. 터미널에서 backend\bundler directory 로 이동 

        cd backend\bundler

1. application.yml 추가
    * [application.yml](application.yml)
    * 변수
      * <MARIADB_USER>, <MARIADB_PASSWORD>

          위에서 생성한 DBMS에 등록된 
          
          MARIADB_USER = 사용자 이름, MARIADB_PASSWORD = 비밀번호

      * <GITHUB_OAUTH_APP_CLIENT_ID>, <GITHUB_OAUTH_APP_CLIENT_SECRET>
          
          github > profile > Settings > Developer settings > OAuth Apps > New OAuth App

          [Github Docs - About OAuth Apps](https://docs.github.com/en/apps/creating-github-apps/creating-github-apps/about-apps#about-oauth-apps)

      * <CLOUD_AWS_S3_ACCESSKEY>, <CLOUD_AWS_S3_SECRETKEY>,<CLOUD_AWS_S3_BUCKET>,<CLOUD_AWS_S3_REGION>

          AWS console - > S3 -> Create bucket -> Amazon S3 관리형 키(SSE-S3) 

          [클라우드 스토리지 | 웹 스토리지| Amazon Web Services](https://aws.amazon.com/ko/s3/?did=ap_card&trk=ap_card)

          [참고 링크](https://velog.io/@modsiw/Spring-Spring-Boot-gradle-S3-React.js-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%85%EB%A1%9C%EB%93%9C-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-1-%EB%B0%B1%EC%97%94%EB%93%9C-%EA%B5%AC%ED%98%84)
    
    * <SECRET_FOR_JWT>
  
        Randomly generated string    
    <details>
    <summary>backend/bundler/src/main/resources/appication.yml</summary>
    <div markdown="1">

        server:
            port: 8080
            servlet:
                context-path: /
                encoding:
                    charset: utf-8
                    enabled: true

        spring:
            datasource:
                driver-class-name: org.mariadb.jdbc.Driver
                url: jdbc:mariadb://localhost:3306/mydata?useUnicode=true&characterEncoding=utf-8
                username: <MARIADB_USER>
                password: <MARIADB_PASSWORD>
            output.ansi.enabled: always
            jpa:
                open-in-view: false
                hibernate:
                ddl-auto: create
                naming:
                    physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
                show-sql: true
                format-sql: true
                properties:
                hibernate:
                    dialect:
                    default_schema:
                defer-datasource-initialization: true

            jackson:
                serialization:
                    write-dates-as-timestamps: false
                time-zome: Asia/Seoul

            security:
                oauth2:
                    client:
                        registration:
                            github:
                                client-id: <GITHUB_OAUTH_APP_CLIENT_ID>
                                client-secret: <GITHUB_OAUTH_APP_CLIENT_SECRET>
                                scope:
                                - repo
                                - read:user

        logging.level:
            org.hibernate.SQL: debug
            org.hibernate.type: trace
            org:
                springframework:
                boot:
                    autoconfigure:
                    security: debug

        jwt.secret: <SECRET_FOR_JWT>

        app:
            jwt:
                secret: <SECRET_FOR_JWT>
                accessTokenPeriod: 1800000
                refreshTokenPeriod: 604800000

            auth:
                tokenSecret: <SECRET_FOR_JWT>
                tokenExpiry: 1800000 #3분
                refreshTokenExpiry: 604800000
            oauth2:
                authorizedRedirectUris:
               - http://localhost:3000/
            servlet:
                multipart:
                    enabled: true
                    max-file-size: 20MB
                    max-request-size: 20MB
        cloud:
            aws:
                s3:
                bucket: <CLOUD_AWS_S3_BUCKET>
                region:
                    static: <CLOUD_AWS_S3_REGION>
                stack:
                    auto: false

                credentials:
                    accessKey: <CLOUD_AWS_S3_ACCESSKEY>
                    secretKey: <CLOUD_AWS_S3_SECRETKEY>

    </div>
    </details>


### CLI

1. 터미널에서 build command 실행

    command 실행 위치 : backend/bundler/

        gradlew build -x test

1. 터미널에서 .jar 파일 실행

    command 실행 위치 : backend/bundler/

        java -jar build\libs\bundler-0.0.1-SNAPSHOT.jar


### IntelliJ IDEA

1. 상단 메뉴 File > Open 
   
   backend\bundler 폴더 열기

2. 상단 메뉴 File > Project Structure > Project > SDK

    azul-17 버전으로 설정

3. 상단 메뉴 Rub > Run BundlerApplication 선택

