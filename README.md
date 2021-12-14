# 👨‍🏫 Seminar Server API 👨‍🎓

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

# Data base Scheme
![image](https://user-images.githubusercontent.com/70942197/145923909-d83c8743-0139-409c-a398-f7935bb52a6d.png)