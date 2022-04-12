# Spring boot를 사용한 로그인

## 프로젝트 요약

로그인 Backend RESTful API 입니다  
이메일+비밀번호, 연락처+비밀번호, 닉네임+비밀번호로 로그인이 가능하도록 구현하였습니다.  
SMS 인증이 된 회원만 회원 가입이 되도록 구현하였습니다. 또 로그인 된 회원만 내 프로필을 볼수 있도록 구현하였습니다.  
마지막으로 SMS 인증이 된 회원만 비밀번호 변경이 가능하도록 구현하였습니다.
Test code를 통해 테스트 할 수 있게 구현하였습니다.

## 기술 스텍

- 서버 : JAVA Spring boot , Spring boot Security, JPA
- DB : H2 database
- 배포 : AWS EC2
- 테스트 : JUnit5, Postman
------------

## 구현 스펙

- ️로그인
  - 이메일 + 비밀번호 로그인
  - 닉네임 + 비밀번호 로그인
  - 전화번호 + 비밀번호 로그인
- 내 프로필 보기
  - JWT 토큰 인증
- 회원 가입
  - SMS 인증
- JWT Access 토큰 재발급
- 예외처리

------------


## 실행 방법

방법 1. 배포환경(AWS EC2)에서 실행

방법 2. 아래 첨부된 명령어를 통해 memberManager-0.0.1-SNAPSHOT.jar 파일 실행
``` 
 java -jar memberManager-0.0.1-SNAPSHOT.jar
```
------------ 

## API 명세

### 회원 가입
- 회원가입
  - http method : POST
  - URL : http://52.52.165.102:80/api/sign-up
  - request body
    - ```
      {
        "name" : "1234567",
        "nickname" : "andy",
        "password" : "your password",
        "phoneNo" : "your phone no",
         "email" : "your@email.com",
      }
      ```
  
- 회원가입 SMS 검증 
  - http method : GET
  - URL : http://52.52.165.102:80/api/sign-up/token
  - request body
    ```
      {
        "phoneNo" : "your phone no",
        "token" : "9f5b4f0c-f9e0-43db-a2c3-bc4711e57ad2"
      }
    ```
  - 주의점 : token은 회원가입할때마다 새롭게 발급되므로, 가입시 console에 나오는 토큰을 입력해줘야함
### 로그인
- 이메일 로그인 
  - http method : POST
  - URL : http://localhost:8080/api/login/email
  - request body
     ```
       { 
         "email" : "your@email.com",
         "password" : "your password"
       }
       ```

- 닉네임 로그인
    - http method : POST
    - URL : http://52.52.165.102:80/api/login/nickname
    - request body
      ```
        {
          "nickname" : "your nickname goes here",
          "password" : "your password goes here"
        } 
        ```
- 전화번호 로그인
    - http method : POST
    - URL : http://52.52.165.102:80/api/login/phone-no
    - request body
    ```
    {
      "phoneNo" : "your phone no goes here",
      "password" : "ghatyvld2@"
    }
    ```

### 프로필 보기 

- 프로필 보기
  - http method : GET
  - URL : http://52.52.165.102:80/api/profile/my
  - header : "Bearer":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWxseSIsImlhdCI6MTY0OTMzNTQ1MiwiZXhwIjoxNjQ5MzM3MjUyfQ.YIsuz2r1eD1rp4P14qQREq_7IBbAcqb9l07ejbblnV_X9wLlHqK8psl26GHUMi5fjh6FAbgClxXl8OKJ6xZJ_w"
  - 주의사항 : 로그인시 발급받은 access token을 header Bearer에 넣어줘야함

### token 재발급
- JWT Access Token 재발급
  - http method : GET
  - URL : http://52.52.165.102:80/api/token/reissued
  - request body
  ```
  {
    "jwtAccessToken" : "",
    "jwtRefreshToken" : ""
  }
  ```

### 비밀번호 변경

- 비밀번호 변경 인증 토큰 발급
  - http method : GET
  - URL : http://52.52.165.102:80/api/password/token
  - request body
  ```
   {
      "phoneNo" : "your phone no"
   }
  ```
    
- 비밀번호 변경
  - http method : PUT
  - URL : http://52.52.165.102:80/api/password
  - request body
  ```
    {
      "phoneNo" : "010-0000-0000",
      "token" : "756fd442-7801-4d8f-a435-c790b58149b0",
      "newPassword" : "put your new password here"
    }
  ```
