# 프론트엔드 프로젝트 빌드

## Contents
---
- [프론트엔드 프로젝트 빌드](#프론트엔드-프로젝트-빌드)
  - [Contents](#contents)
  - [Summary](#summary)
  - [Dependencies](#dependencies)
  - [Node npm 설치](#node-npm-설치)
    - [1. Node.js version.](#1-nodejs-version)
    - [2. Install Node.js](#2-install-nodejs)
  - [모듈 설치](#모듈-설치)
  - [.env 파일 생성](#env-파일-생성)
  - [개발 서버 실행](#개발-서버-실행)
  - [정적 빌드 파일 생성](#정적-빌드-파일-생성)
  - [정적 파일 배포](#정적-파일-배포)
## Summary
---
Node, npm 설치

모듈 설치

개발 서버 실행으로 배포, 정적 파일 빌드 후 WAS로 배포

## Dependencies
---
* Node.js : 18.14.0 
* npm :  9.3.1

## Node npm 설치
---

### 1. Node.js version.

  * Node : 18.14.0 

### 2. Install Node.js

  * Official Downloads(inclue NPM)

    [Download Node.js](https://nodejs.org/en/download/)

## 모듈 설치
---
1. 터미널에서 frontend\react directory로 이동

    cd frontend\react

2. 터미널에서 module 설치

    npm install

## .env 파일 생성
---
API host 주소 설정을 위한 환경설정 파일 생성

1. 프로젝트 root 폴더에 .env 파일 생성
2. .env
    
        REACT_APP_PORT_GLOBAL = "http://localhost:8080"

## 개발 서버 실행
---
1. 터미널에서 npm command 실행
   
    command 실행 위치 : frontend/react/
  
        npm start

2. http://localhost:3000 접속확인

## 정적 빌드 파일 생성
---
1. 터미널에서 npm command 실행

    command 실행 위치 : frontend/react/

        npm run build
        
## 정적 파일 배포

1. WAS DocumentRoot를 'front/react/build' 로 설정
