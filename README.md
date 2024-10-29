# 🔜 MalBut
**외국인을 위한 한국어 보조 학습 서비스입니다.**
<br>

# 👨🏻‍💻 Contributors


## 📖 Development Tech
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
│               │   │── PronunciationJpaRepository.java
│               │   │── ProverbJpaRepository.java
│               │   │── UserJpaRepositoty.java
│               │   └── WordJpaRepositoty.java
│               └── service
│                   ├── ChatService.java
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
