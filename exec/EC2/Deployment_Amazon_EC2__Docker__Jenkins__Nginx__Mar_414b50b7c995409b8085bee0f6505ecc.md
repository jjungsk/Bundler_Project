# Deployment Amazon EC2, Docker, Jenkins, Nginx, MariaDB, SpringBoot, React

## Contents
- [Deployment Amazon EC2, Docker, Jenkins, Nginx, MariaDB, SpringBoot, React](#deployment-amazon-ec2-docker-jenkins-nginx-mariadb-springboot-react)
  - [Contents](#contents)
  - [Amazon EC2](#amazon-ec2)
  - [Docker 설치](#docker-설치)
  - [Docker Network, Volume, Container](#docker-network-volume-container)
  - [Jenkins Default Setting](#jenkins-default-setting)
    - [Jenkins container](#jenkins-container)
    - [Jenkins configure](#jenkins-configure)
  - [Jenkins item](#jenkins-item)
    - [Jenkins - Gitlab repository 연결](#jenkins---gitlab-repository-연결)
    - [React develop docker file \& shell script 작성](#react-develop-docker-file--shell-script-작성)
    - [SpringBoot docker file \& shell script 작성](#springboot-docker-file--shell-script-작성)
    - [Summary](#summary)
    - [build and deploy React app](#build-and-deploy-react-app)

## Amazon EC2

---

Host OS : Ubuntu 20.04 LTS

## Docker 설치

---

[Document](https://docs.docker.com/desktop/install/ubuntu/)

## Docker Network, Volume, Container

---

1. docker network
    
    ```bash
    docker network create bundler-net
    ```
    
2. docker volume
    
    ```bash
    docker volume create bundler-vol
    ```
    
3. docker container 로 MariaDB 배포.
    
    ```bash
    docker run -dp 3939:3306 --network bundler-net --network-alias bundler-db  --mount type=volume,src=bundler-vol,target=/var/lib/mysql --env MARIADB_ROOT_PASSWORD=my-secret-pw --env MARIADB_DATABASE=mydata --env MARIADB_USER=bundler --env MARIADB_PASSWORD=my-secret-pw mariadb:latest
    ```
    
    - 옵션 설명
        - --network-alias bundler-db
            - 같은 docker network에 연결된 다른 컨테이너에서 host 이름으로 사용할 수 있는 alias를 설정한다.
                
                예를 들어) 다른 컨테이너에서 jdbc:mariadb://bundler-db:3306/testdb 로 접속할 수 있다.
                

## Jenkins Default Setting

---

### Jenkins container

프로젝트에서 SpringBoot 3.x 버전을 사용하기에 Java 17을 사용한다.

jenkins에서도 자바 17버전을 지원하기 시작했다. 해당 이미지로 container를 시작한다.

1. run docker container with “jenkins/jenkins:latest-jdk17” image
    
    ```bash
    docker run -d -p 8090:8080 -v /var/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins_real jenkins/jenkins:latest-jdk17
    ```
    
    - 옵션 설명
        - -d : docker container detach 모드로 실행
        - -p : host os의 port와 container port를 바인드?
        - -v : volume mount 설정 docker에서 관리하는 Volume을 통해서 host OS storage에 저장
    - trouble shooting
        - docker run 명령어 실행 후 container 가 목록에서 안 보일 때
            - docker logs <container-id>로 로그 확인
            - message
                
                touch: cannot touch '/var/jenkins_home/copy_reference_file.log': Permission denied
                Can not write to /var/jenkins_home/copy_reference_file.log. Wrong volume permissions?
                
            - cause
                
                현재 사용자에게 volume으로 사용하기 위한 directory의 권한이 없어서 그렇다.
                
                directory 소유자를 바꿔줬다.(1000→현재 사용자의 UID
                
                ```bash
                sudo chown 1000 jenkins_home
                ```
                
    1. connect jenkins container to docker network
        
        ```bash
        docker network inspect bundler-net
        ```
        
2. Install docker in the container
    - command
        - Jenkins container에 접속
            
            ```bash
            docker exec -it jenkins_real bash
            ```
            
        - bash command
            
            ```bash
            apt-get update && \
            apt-get -y install apt-transport-https \
                 ca-certificates \
                 curl \
                 gnupg2 \
                 software-properties-common && \
            curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
            add-apt-repository \
               "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
               $(lsb_release -cs) \
               stable" && \
            apt-get update && \
            apt-get -y install docker-ce
            ```
            
3. 접속
    
    서버 http://서버.도메인:8090 포트로 접속하면 대시보드를 확인할 수 있다.
    

### Jenkins configure

1. 기본 비밀번호
    1. 기본 비밀번호는 Jenkins 서비스 최초 실행시 log로 나온다.
        
        ```bash
        docker logs <container-id || container name>
        ```
        
2. Plugin 설치
****Jenkins 관리 → System Configuration → Plugin Manager → Available Plugin****
    1. Gitlab
        
        ![Untitled](Deployment%20Amazon%20EC2,%20Docker,%20Jenkins,%20Nginx,%20Mar%20414b50b7c995409b8085bee0f6505ecc/Untitled.png)
        
    2. Docker
        
        ![Untitled](Deployment%20Amazon%20EC2,%20Docker,%20Jenkins,%20Nginx,%20Mar%20414b50b7c995409b8085bee0f6505ecc/Untitled%201.png)
        

## Jenkins item

---

### Jenkins - Gitlab repository 연결

1. jenkins item 생성
    1. Free style project
2. jenkins item configure
    1. “소스코드 관리” Git으로 설정
        1. **Repository URL** 입력
        2. Credential 입력
            1. Domain : Global credentials
            2. Kind : Username with password
            3. username & password : validate username & password
    2. Build Trigger 설정
        1. Build when a change is pushed to GitLab 체크
            1. GitLab webhook URL: [http://i8a810.p.ssafy.io:8888/project/Bundler Develop stage](http://i8a810.p.ssafy.io:8888/project/Bundler%20Develop%20stage)
        2. Enabled Gitlab trigger 중 선택
            1. Push, Accepted Merge Request Events
        3. 고급 → Secret token → generate
    3. Build step 추가
        1. Execute shell
            1. Jenkins container 내에서 실행될 shell script 작성할 것
3. Gitlab Webhooks 등록하기
    1. Gitlab project → settings → Webhooks
        1. URL : Jenkins 설정 시 확인한 GitLab webhook URL 입력
        [http://i8a810.p.ssafy.io:8888/project/Bundler Develop stage](http://i8a810.p.ssafy.io:8888/project/Bundler%20Develop%20stage)
        2. Trigger : 위에서 설정한 Trigger 설정
            
            Merge request
            
        3. Add webhook
4. test 후 build 확인
    
    ![Untitled](Deployment%20Amazon%20EC2,%20Docker,%20Jenkins,%20Nginx,%20Mar%20414b50b7c995409b8085bee0f6505ecc/Untitled%202.png)
    

### React develop docker file & shell script 작성

1. write Dockerfile
    
    <aside>
    📄 /frontend/react/Dockerfile
    
    </aside>
    
    ```docker
    # syntax=docker/dockerfile:1
    FROM node:16
    WORKDIR /react
    COPY package.json .
    RUN npm install
    COPY . .
    EXPOSE 3000
    CMD [ "npm", "start" ]
    ```
    
2. Build docker new image
    
    ```bash
    #~/frontend/react
    docker build -t bundler-front-dev .
    ```
    
3. Run docker container with new image
    
    ```bash
    docker run -dp 3000:3000 --mount type=bind,src=$(pwd)/src,target=/react/src --name bundler-front-dev bundler-front-dev
    ```
    
4. shell script
    1. 기존 이미지,컨테이너 id 저장 → 새로운 이미지 빌드 → 기존 컨테이너 중지,삭제 → 기존 이미지 삭제 → 새로운 이미지로 컨테이너 실행
        - 기존 버그 존재하는 스크립트
            
            ```bash
            #!/usr/bin/env bash
            
            IMAGE_NAME="bundler-front-dev"
            CONTAINER_ID="$(docker container ls |grep ${IMAGE_NAME}|awk '{print $1}')"
            IMAGE_ID="$(docker images -q ${IMAGE_NAME})"
            EMPTY_STR=""
            echo "image build start"
            
            docker build -t bundler-front-dev .
            
            echo "image build end "
            echo "container rm start"
            if [ "${CONTAINER_ID}" != "${EMPTY_STR}" ];then
            
                    echo "container rm in start"
                    docker rm -f ${CONTAINER_ID}
                    echo "container rm in end"
            fi
            echo "conatiner rm end"
            
            echo "image rm start"
            if [ "${IMAGE_ID}" != "${EMPTY_STR}" ];then
            
                    echo "image rm in start"
                    docker image rm ${IMAGE_ID}
                    echo "image rm in end"
            fi
            echo "image rm end"
            echo "docker run start"
            docker run -dp 3000:3000 --mount type=bind,src=$(pwd)/src,target=/react/src --name bundler-front-dev bundler-front-dev
            echo "docker run end"
            ```
            
    2. 기존 이미지,컨테이너 id 저장 → 새로운 이미지 빌드 및 새로운 이미지 ID 저장 → 기존 컨테이너 중지,삭제 → 기존 이미지 ID와 새로운 ID 비교해서 달라졌다면 기존 이미지 ID 삭제 → 새로운 이미지로 컨테이너 실행
        - 현재 수정된 스크립트
            
            ```bash
            #!/usr/bin/env bash
            
            IMAGE_NAME="bundler-front-dev"
            CONTAINER_ID="$(docker container ls |grep ${IMAGE_NAME}|awk '{print $1}')"
            IMAGE_ID="$(docker images -q ${IMAGE_NAME})"
            EMPTY_STR=""
            echo "IMAGE : ${IMAGE_ID} "
            echo "CONTAINER : ${CONTAINER_ID}"
            echo "image build start"
            
            docker build -t ${IMAGE_NAME} .
            
            NEW_IMAGE_ID="$(docker images -q ${IMAGE_NAME})"
            
            echo "NEW_IMAGE_ID : ${NEW_IMAGE_ID}"
            
            echo "image build end "
            echo "container rm start"
            if [ "${CONTAINER_ID}" != "${EMPTY_STR}" ];then
            
                    echo "container rm in start"
                    docker rm -f ${CONTAINER_ID}
                    echo "container rm in end"
            fi
            echo "conatiner rm end"
            
            echo "image rm start"
            if [ "${IMAGE_ID}" != "${EMPTY_STR}" ];then
                    if [ "${IMAGE_ID}" != "${NEW_IMAGE_ID}" ];then
                            echo "image rm in start ${IMAGE_ID}"
                            docker image rm ${IMAGE_ID}
                            echo "image rm in end"
                    fi
            fi
            echo "image rm end"
            
            echo "docker run start"
            docker run -dp 3000:3000 --mount type=bind,src=$(pwd)/src,target=/react/src --name bundler-front-dev ${IMAGE_NAME}
            echo "docker run end"
            ```
            
5. jenkins build step → execute shell
    
    ```bash
    cd frontend/react
    sh build.sh
    ```
    
- trouble shooting
    - jenkins build 과정 중 docker run command에서 invalid argument error
        - full messeage
            
            ```bash
            invalid argument "type=bind,src=/var/jenkins_home/workspace/Bundler" for "--mount" flag: target is required
            ```
            
        - cause : jenkins project 이름에따라 workspace directory가 생성되는데 공백이 있어서 $(pwd) 출력에 공백이 포함돼있었다. 그래서 커맨드 실행할 때 중간에 끊어졌다.
        - solution : jenkins dashboard에서 rename 해줬다.

### SpringBoot docker file & shell script 작성

### Summary

<aside>
🛠 3step : “Build .jar file” → “Build docker image” → “Run docker container”

</aside>

1. resources/application.yaml
    
    dataSource url을 위에서 설정한 MariaDB container의 network alias를 호스트로 설정한다.
    
    같은 docker network 상에 존재하기 때문에 3306 port를 사용한다.
    
2. build.gradle
    
    plain-*.jar 파일은 사용 하지 않기 때문에 build에 다음 옵션을 추가 해준다.
    
    ```bash
    jar{
    	enabled = false
    }
    ```
    
3. build .jar file
    
    ```bash
    cd backend/bundler
    sh gradlew clean
    sh gradlew build
    ```
    
4. write Dockerfile
    
    jar파일을 실행해 서버를 구동하는 image를 빌드할 Dockerfile이다.
    
    gradle로 빌드하기 때문에 JAR_FILE 경로를 다음과 같이 해줬다 maven을 사용한다면 변경해야한다.
    
    <aside>
    📄 /backend/bundler/Dockerfile
    
    </aside>
    
    ```docker
    FROM openjdk:17-jdk-alpine
    ARG JAR_FILE=build/libs/\*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
    
5. Build docker image
    
    ```bash
    docker build -t bundler-backend-dev .
    ```
    
6. Run docker container
    
    ```bash
    docker run -dp 8080:8080 --name bundler-backend-dev --network bundler-net bundler-backend-dev
    ```
    
7. Shell script
    
    docker image와 container를 관리할 script를 작성하고 파일로 저장한다.
    
    <aside>
    📄 /backend/bundler/build.sh
    
    </aside>
    
    ```bash
    #!/usr/bin/env bash
    
    IMAGE_NAME="bundler-backend-dev"
    CONTAINER_ID="$(docker container ls |grep ${IMAGE_NAME}|awk '{print $1}')"
    IMAGE_ID="$(docker images -q ${IMAGE_NAME})"
    EMPTY_STR=""
    echo "IMAGE : ${IMAGE_ID} "
    echo "CONTAINER : ${CONTAINER_ID}"
    echo "image build start"
    
    docker build -t ${IMAGE_NAME} .
    
    NEW_IMAGE_ID="$(docker images -q ${IMAGE_NAME})"
    
    echo "NEW_IMAGE_ID : ${NEW_IMAGE_ID}"
    
    echo "image build end "
    echo "container rm start"
    if [ "${CONTAINER_ID}" != "${EMPTY_STR}" ];then
    
            echo "container rm in start"
            docker rm -f ${CONTAINER_ID}
            echo "container rm in end"
    fi
    echo "conatiner rm end"
    
    echo "image rm start"
    if [ "${IMAGE_ID}" != "${EMPTY_STR}" ];then
            if [ "${IMAGE_ID}" != "${NEW_IMAGE_ID}" ];then
                    echo "image rm in start ${IMAGE_ID}"
                    docker image rm ${IMAGE_ID}
                    echo "image rm in end"
            fi
    fi
    echo "image rm end"
    
    echo "docker run start"
    docker run -dp 8080:8080 --name bundler-backend-dev --network bundler-net bundler-backend-dev
    echo "docker run end"
    ```
    
8. Jenkins script
    
    Trigger 가 작동할 때마다 실행될 최종 Jenkins script는 다음과 같다.
    
    ```bash
    cd backend/bundler
    sh gradlew clean
    sh gradlew build
    sh build.sh
    ```
    

### build and deploy React app

두 가지 방법

1. docker container에서 빌드만 진행해 host OS에 저장하고 NGINX root로 설정하는 방법
    - just get static files to host file system
        1. Dockerfile_build 
            
            ```docker
            FROM node:16
            WORKDIR /react
            COPY package.json .
            RUN npm install
            COPY . .
            CMD [ "npm", "run","build" ]
            ```
            
        2. Build image
            
            ```bash
            docker build -t bundler-react-build -f Dockerfile_build .
            ```
            
        3. run container
            
            ```bash
            docker run --mount type=bind,src=$(pwd)/build,target=/react/build bundler-react-build
            ```
            
        4. builed
            
            ```bash
            /home/ubuntu/S08P12A810/frontend/react/build
            ```
            
2. Nginx 까지 dockerizing하고 host NGINX에서 port forwading 하는 방법
    - build image with Nginx
        1. Dockerfile_build 
            
            ```docker
            FROM node:16 AS build
            WORKDIR /react
            COPY package.json .
            RUN npm install
            COPY . .
            RUN npm run build
            
            FROM nginx:alpine
            COPY --from=build /react/build /usr/share/nginx/html
            ```
            
        2. Build image
            
            ```bash
            docker build -t my-image-name -f Dockerfile_build .
            ```
            
        3. run container
            
            ```bash
            docker run -d -p 8082:80 my-image-name
            ```
            
    
    ## Nginx setting
    
    [Ubuntu 20.04에 Nginx 웹 서버를 설치하는 방법](https://ko.linux-console.net/?p=721#gsc.tab=0)
    
    - 설치 및 실행
        
        ```bash
        #설치
        sudo apt update
        sudo apt install nginx
        
        #실행
        sudo systemctl start nginx
        #상태조회
        sudo systemctl status nginx
        
        #시스템 재시작시 자동으로 구동
        sudo systemctl enable nginx
        ```
        
        ![Untitled](Deployment%20Amazon%20EC2,%20Docker,%20Jenkins,%20Nginx,%20Mar%20414b50b7c995409b8085bee0f6505ecc/Untitled%203.png)
        
    - 도메인별 파일 설정 추가
        1. conf 파일 추가 ( /etc/nginx/sites-available/domain.ddd )
            
            root → react build 폴더로 설정
            
            jenkins container의 /var/jenkins_home directory와 binding 된 host OS의 directory 기준 프로젝트 directory
            
            ```bash
            # /etc/nginx/sites-available/domain.ddd
            
            server {
                    listen 80;
                    listen [::]:80;
            
            				#root directory 
            				root /var/jenkins/workspace/JobItem/front/react/build;
            
            				#index file
            				index index.html index.htm index.nginx-debian.html;
            				
            				#domain 
                    server_name i8a810.p.ssafy.io www.i8a810.p.ssafy.io;
            
                    location / {
                            # First attempt to serve request as file, then
                            # as directory, then fall back to displaying a 404.
                            try_files $uri $uri/ =404;
                    }
            }
            ```
            
        2. Nginx 서버가 읽는 사이트 활성화 directory에 연결
            
            ```bash
            sudo ln -s /etc/nginx/sites-available/i8a810.p.ssafy.io /etc/nginx/sites-enabled/
            ```
            
        
    - SSL 설정하기
        1. Cerbot 설치 및 SSL 인증서 발급
            
            ```bash
            sudo snap install certbot --classic
            sudo certbot --nginx -d i8a810.p.ssafy.io
            ```
            
    - reverse proxy : api 요청과 react 요청 구분하기
        
        https://algosketch.tistory.com/128?category=881432
        
        ```bash
        location /api {
            add_header 'Access-Control-Allow-Origin' '*'; # CORS 관련 설정을 nginx 에서도 할 수 있다.
            proxy_pass http://localhost:8000; # :80/api 으로 들어온 요청을 :8000/api 으로 포워딩한다.
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Host $http_host;
          }
        ```
        
    - trouble shooting
        - 새로고침시 404
            
            try_files $uri $uri/ /index.html =404; 추가
            
            ```bash
            server {
            ...
                    location / {
            ...
                            try_files $uri $uri/ /index.html =404;
            ...
                    }
            ...
            
            }
            ```
            
        - 403 error
            - nginx를 통해 제공하고자 하는 root folder에 대한 nginx service user의 권한이 없어서 그렇다. nginx.conf 를  확인해 nginx가 어떤 유저로 실행되는지 확인하고, dir에 대한 권한을 가진 그룹에 추가해주자.
            
            ```bash
            sudo gpasswd -a www-data ubuntu
            ```