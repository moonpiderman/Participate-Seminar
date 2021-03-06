# π¨βπ« Seminar Server API π¨βπ

- κ°μ¬μ μκ°μμ΄ μ°Έμ¬ν  μ μλ μΈλ―Έλ μλ² API μλλ€.
- κ°μ¬λ νλμ μΈλ―Έλμ μ°Έμ¬ν  μ μμΌλ©°, ν μΈλ―Έλμλ μ¬λ¬λͺμ κ°μ¬κ° μ‘΄μ¬ν  μ μμ΅λλ€.
- ν μΈλ―Έλμλ λ€μμ μ°Έμ¬μκ° μ‘΄μ¬ν  μ μμΌλ©°, μ°Έμ¬μλ μ¬λ¬ μΈλ―Έλμ μ°Έμ¬ν  μ μμ΅λλ€.

# Tech Stack
![image](https://user-images.githubusercontent.com/70942197/153562892-0b77b664-4e9c-4d1b-8bef-3e46f1ca1683.png)

# API Docs
π [Notion Link](https://eggplant-sumac-51e.notion.site/Seminar-API-Document-a37fea9c43c14153a3c42b803ec63769)

# Create Database
```shell
bash seminar/scripts/bash/init-db.sh
```

# Project Structure
```text
βββ build.gradle.kts
βββ gradlew
βββ scripts
β   βββ bash/init-db.sh
β   βββ sql/init-db.sql
βββ src
    βββ main
        βββ kotlin
        β    βββ com.wafflestudio.seminar
        β        βββ domain
        β        β   βββ USER - model, controller, repository, service
        β        β   β          βββ User
        β        β   β          βββ Participant
        β        β   β          βββ Instructor
        β        β   βββ SEMINAR - model, controller, repository, service
        β        β                 βββ Seminar
        β        β                 βββ SeminarParticipant
        β        βββ global
        β            βββ auth - JWT, SigninAuthenticationFilter
        β            βββ common.exception - ErrorType(ENUM), ErrorResponse
        β            βββ config
        β                βββ SecurityConfig
        β
        βββ resources
            βββ application.yml
```

# Database Scheme
![image](https://user-images.githubusercontent.com/70942197/145923909-d83c8743-0139-409c-a398-f7935bb52a6d.png)
