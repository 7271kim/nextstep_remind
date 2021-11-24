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

## 기능 요구사항
 - 지하철 노선 : 생성 / 목록 조회 / 조회 / 수정 / 삭제 / 구간등록 / 구간삭제 API 개발

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


## 프로그래밍 요구사항
 - ATDD가 존재해야 한다.

## 핵심 기능 목록
 - [x] 두 역을 새로 등록할 수 있고 역 사이에 새로운 역을 등록할 수 있다.
 - [x] 역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없음
 - [x] 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없음
 - [x] 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없음
 - [x] 기존 종점이 제거될 경우 다음으로 오던 역이 종점이 된다.
 - [x] 구간이 1개일 때는 구간을 제거 할 수 없다.
 

## 📝 License
This project is MIT licensed.
