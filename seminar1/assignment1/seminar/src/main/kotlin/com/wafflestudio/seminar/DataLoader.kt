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

    }
}
