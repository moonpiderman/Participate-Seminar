package com.wafflestudio.seminar

import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.os.repository.OperatingSystemRepository
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.repository.SurveyResponseRepository
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.ParticipantService
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Component
class DataLoader(
    private val operatingSystemRepository: OperatingSystemRepository,
    private val surveyResponseRepository: SurveyResponseRepository,
    private val userRepository: UserRepository,
    private val participantRepository: ParticipantRepository,
    private val instructorRepository: InstructorRepository,
    private val seminarRepository: SeminarRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val participantService: ParticipantService,
) : ApplicationRunner {
    // 어플리케이션 동작 시 실행
    override fun run(args: ApplicationArguments) {
        val windows = OperatingSystem(name = "Windows", price = 200000, description = "Most favorite OS in South Korea")
        val macos =
            OperatingSystem(name = "MacOS", price = 300000, description = "Most favorite OS of Seminar Instructors")
        val linux = OperatingSystem(name = "Linux", price = 0, description = "Linus Benedict Torvalds")
        val others = OperatingSystem(name = "Others", price = 0, description = "")
        operatingSystemRepository.save(windows)
        operatingSystemRepository.save(macos)
        operatingSystemRepository.save(linux)

        BufferedReader(InputStreamReader(ClassPathResource("data/example_surveyresult.tsv").inputStream)).use { br ->
            br.lines().forEach {
                val rawSurveyResponse = it.split("\t")
                val newSurveyResponse = SurveyResponse(
                    timestamp = LocalDateTime.parse(
                        rawSurveyResponse[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    ),
                    os = operatingSystemRepository.findByNameEquals(rawSurveyResponse[1]) ?: others,
                    springExp = rawSurveyResponse[2].toInt(),
                    rdbExp = rawSurveyResponse[3].toInt(),
                    programmingExp = rawSurveyResponse[4].toInt(),
                    major = rawSurveyResponse[5],
                    grade = rawSurveyResponse[6],
                    user = null,
                )
                surveyResponseRepository.save(newSurveyResponse)
            }
        }

        // Samples for request test
//        val userSample = User(
//            email = "sample@sample.com",
//            name = "sample",
//            password = "sample",
//            roles = "instructor,participant",
//        )
//
//        val userSample2 = User(
//            email = "aaaa@aaaa.com",
//            name = "aaaa",
//            password = "aaaa",
//            roles = "participant",
//        )
//
//        val participantProfileSample = ParticipantProfile(
//            university = "Catholic Univ.",
//            accepted = true,
//            user = userSample,
//        )
//
//        val participantProfileSample2 = ParticipantProfile(
//            university = "SNU",
//            accepted = true,
//            user = userSample2,
//        )
//
//        val seminarSample: Seminar = Seminar(
//            name = "seminar",
//            capacity = 10,
//            count = 5,
//            time = LocalTime.NOON,
//            online = false
//        )
//
//        val seminarParticipantSample = SeminarParticipant(
//            seminar = seminarSample,
//            isActive = true,
//            droppedAt = null,
//            participantProfile = participantProfileSample,
//        )
//
//        val seminarParticipantSample2 = SeminarParticipant(
//            seminar = seminarSample,
//            isActive = true,
//            droppedAt = null,
//            participantProfile = participantProfileSample2
//        )
//
//        val instructorProfileSample: InstructorProfile = InstructorProfile(
//            company = "Naver",
//            year = 1,
//            user = userSample,
//            seminar = seminarSample
//        )
//        userRepository.save(userSample)
//        seminarRepository.save(seminarSample)
        // InstructorProfile.seminar 의 cascadeType 이 MERGE 라면 save 가 존재해야하나,
        // PERSIST 라면 그렇지 않다. 이유는 instructorRespo 가 save 하면서 seminar 영역으로 들어가
        // 거기서 save 를 진행하기 때문에 seminarRepo 의 save 가 또 실행된다면 충돌을 일으킨다.
        // 이러할 때 PERSIST 를 사용한다.
//        seminarParticipantRepository.save(seminarParticipantSample)
//        instructorRepository.save(instructorProfileSample)

//        println(jwtTokenProvider.generateToken(userSample.email))
    }
}
