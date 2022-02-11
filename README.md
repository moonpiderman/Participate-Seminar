# 👨‍🏫 Seminar Server API 👨‍🎓

- 강사와 수강생이 참여할 수 있는 세미나 서버 API 입니다.
- 강사는 하나의 세미나에 참여할 수 있으며, 한 세미나에는 여러명의 강사가 존재할 수 있습니다.
- 한 세미나에는 다수의 참여자가 존재할 수 있으며, 참여자는 여러 세미나에 참여할 수 있습니다.

# Tech Stack
![image](https://user-images.githubusercontent.com/70942197/153562892-0b77b664-4e9c-4d1b-8bef-3e46f1ca1683.png)

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
