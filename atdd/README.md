# 지하철 노선 관리 <img alt="npm" src="https://img.shields.io/badge/npm-v6.14-blue"><img alt="node" src="https://img.shields.io/badge/node-14.18-blue"><img alt="python" src="https://img.shields.io/badge/python-v2.7-blue">

## 🚀 Getting Started

### Install
#### node 설치 후 ( 윈도우 기준 )
```
npm install --global --production windows-build-tools ( 설치시 멈춘것 처럼 보이면 제어판으로 파이썬 및 C++2017 설치 되었나 확인)
or
파이썬 2.7
Visual C++ 2017 설설치 필요
```
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```

## 기능 요구사항 ( API 개발 )
 - 지하철 역 : 생성 / 목록 조회 / 삭제
 - 지하철 노선 : 생성 / 목록 조회 / 조회 / 수정 / 삭제 
 - 지하철 구간 : 등록 / 삭제 API 개발
 - 회원 : 가입 / 목록조회 / 회원 조회 / 업데이트 / 삭제 ( admin계정만 가능하다 )
 - 내 정보 : 조회 / 업데이트 / 삭제 ( 로그인 계정만 가능하다 )
 - 즐겨찾기 : 생성 / 목록 조회 / 삭제 
 - 경로 검색

<p align="center">
    <img width="300px;" src="images/노선관리.png" alt="노선괸리"> <img width="300px;" src="images/요금조회.png" alt="요금조회"> <img width="300px;" src="images/로그인.png" alt="로그인">
</p>
<p align="center">
    <img width="800px;" src="images/내정보.png" alt="내정보">
</p>
<p align="center">
    <img width="800px;" src="images/즐겨찾기.png" alt="즐겨찾기">
</p>

### 지하철 역 생성 request / response
```
POST /stations HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8

{
    "name": "강남역"
}

HTTP/1.1 201 
Location: /stations/1
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

{
    "id": 1,
    "name": "강남역",
    "createdDate": "2020-11-13T09:11:51.997",
    "modifiedDate": "2020-11-13T09:11:51.997"
}
```

### 지하철 역 목록 조회 request / response
```
GET /stations HTTP/1.1
accept: application/json
host: localhost:49468

HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

[
    {
        "id": 1,
        "name": "강남역",
        "createdDate": "2020-11-13T09:11:51.997",
        "modifiedDate": "2020-11-13T09:11:51.997"
    },
    {
        "id": 2,
        "name": "교대역",
        "createdDate": "2020-11-13T09:11:51.997",
        "modifiedDate": "2020-11-13T09:11:51.997"
    }
]

```

### 지하철 역 삭제 request / response
```
DELETE /stations/1 HTTP/1.1
accept: */*
host: localhost:49468

HTTP/1.1 204 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 지하철 노선 생성 request / response
```
POST /lines HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8

{
    "color": "bg-red-600",
    "name": "신분당선",
    "upStationId": "1",
    "downStationId": "2",
    "distance": "10"
}

HTTP/1.1 201 
Location: /lines/1
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

{
    "id": 1,
    "name": "신분당선",
    "color": "bg-red-600",
    "createdDate": "2020-11-13T09:11:51.997",
    "modifiedDate": "2020-11-13T09:11:51.997"
}
```

### 지하철 노선 목록 조회 request / response
```
GET /lines HTTP/1.1
accept: application/json
host: localhost:49468

HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

[
    {
        "id": 1,
        "name": "신분당선",
        "color": "bg-red-600",
        "stations": [
            
        ],
        "createdDate": "2020-11-13T09:11:52.084",
        "modifiedDate": "2020-11-13T09:11:52.084"
    },
    {
        "id": 2,
        "name": "2호선",
        "color": "bg-green-600",
        "stations": [
            
        ],
        "createdDate": "2020-11-13T09:11:52.098",
        "modifiedDate": "2020-11-13T09:11:52.098"
    }
]

```
### 지하철 노선 조회 request / response
```
GET /lines/1 HTTP/1.1
accept: application/json
host: localhost:49468

HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

{
    "id": 1,
    "name": "신분당선",
    "color": "bg-red-600",
    "stations": [
        {
            "id": 1,
            "name": "강남역",
            "createdDate": "2020-11-13T12:17:03.075",
            "modifiedDate": "2020-11-13T12:17:03.075"
        },
        {
            "id": 2,
            "name": "역삼역",
            "createdDate": "2020-11-13T12:17:03.092",
            "modifiedDate": "2020-11-13T12:17:03.092"
        }
    ],
    "createdDate": "2020-11-13T09:11:51.997",
    "modifiedDate": "2020-11-13T09:11:51.997"
}
```
### 지하철 노선 수정 request / response
```
PUT /lines/1 HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8
content-length: 45
host: localhost:49468

{
    "color": "bg-blue-600",
    "name": "구분당선"
}

HTTP/1.1 200 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```
### 지하철 노선 삭제 request / response
```
DELETE /lines/1 HTTP/1.1
accept: */*
host: localhost:49468

HTTP/1.1 204 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```
### 구간 등록 request / response
```
POST /lines/1/sections HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8
host: localhost:52165

{
    "downStationId": "4",
    "upStationId": "2",
    "distance": 10
}

HTTP/1.1 200 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 지하철 구간 삭제  request / response
```
DELETE /lines/1/sections?stationId=2 HTTP/1.1
accept: */*
host: localhost:52165

HTTP/1.1 204 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 회원 가입 request / response
```
POST /members HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8

{
    "email": "7271kim@naver.com",
    "password" : "test",
    "age" : 20
}

HTTP/1.1 201 
Location: /members/1
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 회원 목록 조회 request / response
```
GET /members HTTP/1.1
accept: application/json
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY


HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

[
    {
        "id": 1,
        "email" : "7271kim@naver.com",
        "age" : 20
    },
    {
        "id": 2,
        "email" : "7272kim@naver.com",
        "age" : 21
    }
]
```

### 회원 조회 request / response
```
GET /members/1 HTTP/1.1
accept: application/json
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY


HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

{
    "id": 1,
    "email" : "7271kim@naver.com",
    "age" : 20
}

```

### 회원 업데이트 request / response
```
PUT /members/1 HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8
content-length: 45
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY


{
    "email": "7271kim@naver.com",
    "password" : "test",
    "age" : 20,
    "activeType" : 1,
    "userType" : 1
}

HTTP/1.1 200 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 회원 삭제 request / response
```
DELETE /members/1 HTTP/1.1
accept: */*
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY


HTTP/1.1 204 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 내 정보 확인 request / response
```
GET /members/me HTTP/1.1
accept: application/json
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY

HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT

{
    "id": 1,
    "email" : "7271kim@naver.com",
    "age" : 20
}
```

### 내 정보 업데이트 request / response
```
PUT /members/me HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8
content-length: 45
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY


{
    "email": "7271kim@naver.com",
    "password" : "test",
    "age" : 20
}

HTTP/1.1 200 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 내 계정 삭제 request / response
```
DELETE /members/me HTTP/1.1
accept: */*
host: localhost:49468
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY


HTTP/1.1 204 
Date: Fri, 13 Nov 2020 00:11:51 GMT
```

### 경로 조회 request / response
```
GET /paths?source=1&target=6 HTTP/1.1
accept: */*
host: localhost:52165
Headers: Accept=application/json
        Content-Type=application/json; charset=UTF-8

HTTP/1.1 200 
Content-Type: application/json
Date: Fri, 13 Nov 2020 00:11:51 GMT
{
    "stations": [
        {
            "id": 5,
            "name": "양재시민의숲역",
            "createdAt": "2020-05-09T23:54:12.007"
        },
        {
            "id": 4,
            "name": "양재역",
            "createdAt": "2020-05-09T23:54:11.995"
        },
        {
            "id": 1,
            "name": "강남역",
            "createdAt": "2020-05-09T23:54:11.855"
        },
        {
            "id": 2,
            "name": "역삼역",
            "createdAt": "2020-05-09T23:54:11.876"
        },
        {
            "id": 3,
            "name": "선릉역",
            "createdAt": "2020-05-09T23:54:11.893"
        }
    ],
    "distance": 40
}
```

### 토큰 발급(로그인) request / response
```
POST /login/token HTTP/1.1
content-type: application/json; charset=UTF-8
accept: application/json
{
    "password": "password",
    "email": "email@email.com"
}

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 27 Dec 2020 04:32:26 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY"
}
```

### 즐겨찾기 생성 request / response
```
POST /favorites HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY
accept: */*
content-type: application/json; charset=UTF-8
content-length: 27
host: localhost:50336
connection: Keep-Alive
user-agent: Apache-HttpClient/4.5.13 (Java/14.0.2)
accept-encoding: gzip,deflate
{
    "source": "1",
    "target": "3"
}

HTTP/1.1 201 Created
Keep-Alive: timeout=60
Connection: keep-alive
Content-Length: 0
Date: Sun, 27 Dec 2020 04:32:26 GMT
Location: /favorites/1
```

### 즐겨찾기 목록 조회 request / response
```
GET /favorites HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY
accept: application/json
host: localhost:50336
connection: Keep-Alive
user-agent: Apache-HttpClient/4.5.13 (Java/14.0.2)
accept-encoding: gzip,deflate

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 27 Dec 2020 04:32:26 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
    {
        "id": 1,
        "source": {
            "id": 1,
            "name": "강남역",
            "createdDate": "2020-12-27T13:32:26.364439",
            "modifiedDate": "2020-12-27T13:32:26.364439"
        },
        "target": {
            "id": 3,
            "name": "정자역",
            "createdDate": "2020-12-27T13:32:26.486256",
            "modifiedDate": "2020-12-27T13:32:26.486256"
        }
    }
]
```
### 즐겨찾기 삭제 request / response
```
DELETE /favorites/1 HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY
accept: */*
host: localhost:50336
connection: Keep-Alive
user-agent: Apache-HttpClient/4.5.13 (Java/14.0.2)
accept-encoding: gzip,deflate



HTTP/1.1 204 No Content
Keep-Alive: timeout=60
Connection: keep-alive
Date: Sun, 27 Dec 2020 04:32:26 GMT

```

## 프로그래밍 요구사항
 - ATDD가 존재해야 한다.

### 경로 조회 시 거리 기준 요금 정보 포함하기
 - 기본운임(10㎞ 이내) : 기본운임 1,250원
 - 0km초과∼50km까지(5km마다 100원)
 - 50km초과 시 (8km마다 100원)
 
### 노선별 추가 요금 정책 추가
 - 노선에 추가 요금 필드를 추가
 - 추가 요금이 있는 노선을 이용 할 경우 측정된 요금에 추가
 - 경로 중 추가요금이 있는 노선을 환승 하여 이용 할 경우 가장 높은 금액의 추가 요금만 적용
 
### 연령별 할인 정책 추가
 - 청소년: 운임에서 350원을 공제한 금액의 20%할인
 - 어린이: 운임에서 350원을 공제한 금액의 50%할인
 
## 핵심 기능 목록
 - [x] 두 역을 새로 등록할 수 있고 역 사이에 새로운 역을 등록할 수 있다.
 - [x] 역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없음
 - [x] 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없음
 - [x] 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없음
 - [x] 기존 종점이 제거될 경우 다음으로 오던 역이 종점이 된다.
 - [x] 구간이 1개일 때는 구간을 제거 할 수 없다.

## 📝 License
This project is MIT licensed.
