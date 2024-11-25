# 🔜 MalBut
**외국인을 위한 한국어 보조 학습 서비스 "말벗"입니다.**
<br>

# 👨🏻‍💻 Contributors
|  <div align = center>조현태 </div> | <div align = center> 이준수 </div> | <div align = center> 소병욱 </div>                                                                                                                                                                                                                                                                                                                           |
|:----------|:----------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|<div align = center> <img src = "https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fnoticon-static.tammolo.com%2Fdgggcrkxq%2Fimage%2Fupload%2Fv1567128822%2Fnoticon%2Fosiivsvhnu4nt8doquo0.png&blockId=865f4b2a-5198-49e8-a173-0f893a4fed45&width=256" width = "17" height = "17"/> [hyuntae99](https://github.com/hyuntae99) </div> | <div align = center> <img src = "https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fnoticon-static.tammolo.com%2Fdgggcrkxq%2Fimage%2Fupload%2Fv1567128822%2Fnoticon%2Fosiivsvhnu4nt8doquo0.png&blockId=865f4b2a-5198-49e8-a173-0f893a4fed45&width=256" width = "17" height = "17"/> [elephant0302](https://github.com/elephant0302) </div> | <div align = center> <img src = "https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fnoticon-static.tammolo.com%2Fdgggcrkxq%2Fimage%2Fupload%2Fv1567128822%2Fnoticon%2Fosiivsvhnu4nt8doquo0.png&blockId=865f4b2a-5198-49e8-a173-0f893a4fed45&width=256" width = "17" height = "17"/> [Cybecho](https://github.com/Cybecho) </div>
<br>

# 📖 Development Tech
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
<br>

# 💼 Server Architecture
<img src="https://velog.velcdn.com/images/jmjmjmz732002/post/a6c7a7be-ff27-4723-bfe2-d458ed641fab/image.png">
<br>

# 📱 Service Overview
<img src="https://velog.velcdn.com/images/hyuntae99/post/9e5253e6-cbca-43e8-bd13-5a2a4c83f6f8/image.png" width="700">
<br>

# 🗂️ Directory
```
├── java
│   └── com
│       └── hyunn
│           └── malBut
│               ├── config
│               │   ├── SwaggerConfig.java
│               │   └── WebConfig.java
│               ├── controller
│               │   ├── ChatController.java
│               │   ├── MainController.java
│               │   ├── PronunciationController.java
│               │   ├── QuizController.java
│               │   └── UserController.java
│               ├── dto
│               │   ├── request
│               │   │   ├── ChatRequest.java
│               │   │   ├── EvaluateProverbRequest.java
│               │   │   └── GradeWordRequest.java
│               │   └── response
│               │       ├── ApiStandardResponse.java
│               │       ├── ChatResponse.java
│               │       ├── ErrorResponse.java
│               │       ├── EvaluateProverbResponse.java
│               │       ├── FlaskEvaluateResponse.java
│               │       ├── GradeWordResponse.java
│               │       ├── ProninciationResponse.java
│               │       ├── QuizProverbResponse.java
│               │       ├── QuizWordResponse.java
│               │       └── ScriptReponse.java
│               ├── entity
│               │   │── BaseEntity.java
│               │   │── Chat.java
│               │   │── Proninciation.java
│               │   │── Proverb.java
│               │   │── User.java
│               │   └── Word.java
│               ├── exception
│               │   │── ApiKeyNotValidException.java
│               │   │── ApiNotFoundException.java
│               │   │── ErrorStatus.java
│               │   │── GlobalExceptionHandler.java
│               │   │── UnauthorizedException.java
│               │   └── UserNorFoundException.java
│               ├── repository
│               │   │── ChatJpaRepository.java
│               │   │── PronunciationJpaRepository.java
│               │   │── ProverbJpaRepository.java
│               │   │── UserJpaRepositoty.java
│               │   └── WordJpaRepositoty.java
│               └── service
│                   ├── ChatService.java
│                   ├── FlaskPronunciationService.java
│                   ├── MailService.java
│                   ├── PronunciationService.java
│                   ├── QuizService.java
│                   ├── SchedulerService.java
│                   └── UserService.java
└── resources
    ├── config 
    │   └── application-local.yml
    ├── templates
    │   ├── chat.html
    │   ├── index.html
    │   ├── pronunciation.html
    │   ├── proverb.html
    │   ├── quiz.html
    │   ├── subscribe.html
    │   └── withdraw.html
    └── application.yml
```

# 📝 Service

## 1. 메인 페이지

원할한 사용자 경험을 위해서 로그인 없이 사용 가능하도록 만들었습니다. <br>
최대한 간결한 UI로 구현하여 사용성을 높였습니다.

<img src= "https://velog.velcdn.com/images/hyuntae99/post/9ff524cb-0cdc-4a2b-b727-e4aaa892d734/image.gif">
<br><br>

## 2. 한국어 지도 챗봇 기능

먼저 한국어 지도 챗봇 기능입니다.<br>
저희는 성능과 가격 모두 준수한 LLM인 GPT 4o-mini를 사용했습니다.<br>
특정 기능에 성능을 높이기 위해서 파인 튜닝을 진행하였습니다.<br>
이를 통해서 자연스럽게 채팅을 통해 한국어 표현 교정 뿐만 아니라 감정적인 공유나 정보 습득 등 여러 측면에서 도움이 될 것입니다.

<img src= "https://velog.velcdn.com/images/hyuntae99/post/afe16e65-5b46-4c56-8cac-eae53ef34701/image.gif">
<br><br>

### 예시
<img src="https://velog.velcdn.com/images/hyuntae99/post/14bfcfce-1933-4f0c-9e55-88ac4080dd52/image.png" width="700">
<br><br>

## 3. 발음 학습 및 평가 기능

발음 학습 및 평가 기능입니다.<br>
난이도를 상중하로 대본을 선택할 수 있게 만들었습니다.<br>
Goole STT/TTS를 통해서 대본의 정확한 발음을 제공하고 이를 녹음하여 텍스트화하여 평가합니다.<br>
이후 [flask 서버의 모델](https://github.com/mal-but/flask)을 활용하여 형태소 분석과 코사인 유사도, 전달 점수를 종합적으로 고려하여 점수와 피드백을 반환합니다.

<img src= "https://velog.velcdn.com/images/hyuntae99/post/8fc913e5-2241-43a9-97b5-59187677fab7/image.gif">
<br><br>

### 예시
<img src="https://velog.velcdn.com/images/hyuntae99/post/c042be62-631c-4b5c-a0a5-5c5e630ac15c/image.png" width="400"><img src="https://velog.velcdn.com/images/hyuntae99/post/ff4830bc-72e6-424a-b9a1-cf92ce147325/image.png" width="400">
<br><br>

## 4. 단어 학습 및 평가 기능

단어 학습 및 평가 기능입니다.<br>
필수 영단어 같은 학습 자료를 적절히 선별하여 이를 활용해서 간단한 객관식 문제를 제공합니다.<br>
난이도 별로 구성하여 수준에 맞는 학습을 할 수 있도록 했습니다.<br>

<img src= "https://velog.velcdn.com/images/hyuntae99/post/7af66baf-235e-4fcf-a9a1-f4faf08b6433/image.gif">
<br><br>

### 예시
<img src="https://velog.velcdn.com/images/hyuntae99/post/bc0a088e-9659-48c2-94b1-5296e3ea4fd5/image.png" width="400"><img src="https://velog.velcdn.com/images/hyuntae99/post/e1e3e723-1fb4-4d61-976e-88b0a0c79a24/image.png" width="400">
<br><br>

## 5. 속담/숙어 학습 및 평가 기능

속담/숙어 학습 및 평가 기능입니다.<br>
앞의 단어 학습 및 평가 기능과 차별화를 두기 위해서 주관식 문제를 제공하고자 했습니다.<br>
그 이유는 속담과 구문 같은 문장은 답이 아니더라도 비슷한 의미를 가질 수 있기 때문입니다.<br>
AWS freetire 서버의 한계로 flask의 모델을 사용이 힘들었습니다.<br>
그래서 파인튜닝한 LLM을 모델을 사용하여 문맥을 파악하고 점수와 평가를 하도록 했습니다.

<img src= "https://velog.velcdn.com/images/hyuntae99/post/9b22760f-d7e5-4238-b755-b95088589bb9/image.gif">
<br><br>

### 예시
<img src="https://velog.velcdn.com/images/hyuntae99/post/3eb733a6-8efc-4b52-b30f-23c430799836/image.png" width="400"><img src="https://velog.velcdn.com/images/hyuntae99/post/355eea13-0c5f-4c43-8bff-9a40adb67d5a/image.png" width="400">

## 6. 구독 서비스

구독 서비스입니다.<br>
한국어 속담이나 사자성어, 필수 구문 같은 데이터를 매일 유저의 이메일로 발송하는 기능을 만들었습니다.<br>
회원가입이 따로 없는 서비스이기 때문에 이메일 인증을 마치면 사용할 수 있도록 만들었습니다.

<img src="https://velog.velcdn.com/images/hyuntae99/post/f3f00c6d-af73-4db6-9b97-1beefcd3a260/image.gif">
<br><br>

### 이메일 수신 예시
<img src= "https://velog.velcdn.com/images/hyuntae99/post/c72915d5-0c39-4bbc-a7b7-5273a4bc0ff3/image.png" width = "400">
<br><br>

# ✨ Project Evaluation

## 1. Background

<img src="https://velog.velcdn.com/images/hyuntae99/post/7157028c-a99e-4112-9d20-5a7a65cb0b95/image.png">
<br>최근 한류 열풍으로 인해 전세계적으로 한국에 대한 관심도가 높아졌습니다.
<br>다음 자료를 보시면, 2023년 기준 전 세계 한류 팬 수는 약 1억 8000만 명으로 조사되었다하며 이는 10년사이 약 20배가량 증가한 수치입니다.
<br><br>

<img src="https://velog.velcdn.com/images/hyuntae99/post/7dddbab3-fb89-43b9-998f-bf2c4b4d69e0/image.png">
<br>이러한 한류 열풍 덕분에 외국인 유학생 수가 크게 증가했습니다.
<br>이에 발맞춰 문화체육관광부는 2027년까지 세종학당을 전 세계 350개소로 확대할 계획을 추진하고 있습니다. 
<br><br>

<img src="https://velog.velcdn.com/images/hyuntae99/post/a834db20-ba23-498a-8d87-60ae08da8608/image.png">
<br><br>

## 2. Benefits

<img src="https://velog.velcdn.com/images/hyuntae99/post/5c32554a-bc97-4429-936d-2e8dbcfb99ac/image.png">
<br><br>

## 3. Limitations

<img src="https://velog.velcdn.com/images/hyuntae99/post/0737beda-c9c3-4401-9f64-b9022130a9b3/image.png">
<br>첫번째 한계는 영어만을 사용한 프로젝트라는 것입니다.
<br>저희 프로젝트는 전세계적으로 가장 많이 통용되는 영어를 기반으로 한국어 보조 학습 서비스를 구현했습니다.
<br>하지만 다음 자료를 보면 대다수의 유학생들은 영어권 나라에서 오지 않았음을 알 수 있습니다.
<br>추후 중국어, 베트남어 등의 버전을 추가하여 유학생 자국민의 언어로 좀 더 편한 경험을 줄 수 있도록 확장할 계획입니다.
<br><br>

<img src="https://velog.velcdn.com/images/hyuntae99/post/86244f40-9f96-486a-8b36-c2d480785dba/image.png">
<br>두번째는 제공하는 한국어 교육 수준입니다.
<br>이 자료를 보면 어학당 수강생 중 학업이나 취업을 목적으로 한국어를 배우는 경우도 많다는 것을 알 수 있습니다.
<br>이러한 경우, 저희 한국어 학습 프로젝트만으로는 학습이 충분하지 않을 수 있습니다. 
<br>많은 외국인 학습자들이 'TOPIK' 자격증을 취득해 지원금과 장학금을 받거나 스펙을 쌓고자 한다고 알고 있습니다.
<br>따라서, 세종학당과 같은 전문 학습 기관이나 TOPIK을 주관하는 국립국제교육원과 연계한다면,
<br>보다 높은 수준의 학습 경험을 제공할 수 있을 것이라고 생각합니다.
<br><br>

### 더 많은 정보는 [발표영상](https://www.youtube.com/watch?v=ivX7MP9aIa0)에서 확인하실 수 있습니다.
<br>