# 👨‍🏫 Seminar Server API 👨‍🎓

- 강사와 수강생이 참여할 수 있는 세미나 서버 API 입니다.
- 강사는 하나의 세미나에 참여할 수 있으며, 한 세미나에는 여러명의 강사가 존재할 수 있습니다.
- 한 세미나에는 다수의 참여자가 존재할 수 있으며, 참여자는 여러 세미나에 참여할 수 있습니다.

# Tech Stack
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white"/></a> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/></a> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/></a> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a> <img src="https://img.shields.io/badge/JWT-6DB33F?style=flat-square&logo=Json Web Tokens&logoColor=white"/></a> <img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=NGINX&logoColor=white"/></a> <img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=Amazon AWS&logoColor=white"/></a>

# API Docs
🔗 [Notion Link](https://eggplant-sumac-51e.notion.site/Seminar-API-Document-a37fea9c43c14153a3c42b803ec63769)

# Create Database
```shell
bash seminar/scripts/bash/init-db.sh
```

# Project Structure
```text
├── build.gradle.kts
├── gradlew
├── scripts
│   ├── bash/init-db.sh
│   └── sql/init-db.sql
└── src
    └── main
        ├── kotlin
        │    └── com.wafflestudio.seminar
        │        ├── domain
        │        │   ├── USER - model, controller, repository, service
        │        │   │          ├── User
        │        │   │          ├── Participant
        │        │   │          └── Instructor
        │        │   └── SEMINAR - model, controller, repository, service
        │        │                 ├── Seminar
        │        │                 └── SeminarParticipant
        │        └── global
        │            ├── auth - JWT, SigninAuthenticationFilter
        │            ├── common.exception - ErrorType(ENUM), ErrorResponse
        │            └── config
        │                └── SecurityConfig
        │
        └── resources
            └── application.yml
```

# Database Scheme
![image](https://user-images.githubusercontent.com/70942197/145923909-d83c8743-0139-409c-a398-f7935bb52a6d.png)