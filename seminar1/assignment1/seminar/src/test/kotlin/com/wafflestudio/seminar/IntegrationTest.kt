package com.wafflestudio.seminar

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.UserService
import org.json.JSONObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.*
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.time.LocalTime
import javax.transaction.Transactional

@ActiveProfiles("test")
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class IntegrationTest(
    private val mockMvc: MockMvc,
    private val userService: UserService,
) {

    @BeforeEach
    fun `회원가입`() {
        signupAsParticipantUser("bomoonP").andExpect {
            status { isNoContent() }
            header { exists("Authentication") }
        }

        signupAsInstructorUser("bomoonI").andExpect {
            status { isNoContent() }
            header { exists("Authentication") }
        }
    }

    @Test
    @Transactional
    fun `id를 통한 회원 조회`() {
        // db를 새로 켰을 때 조회가 불가능해짐.
        // 여기서도 userService 를 끌어와서 실패로직을 같이 만드는 것이 맞나?
        // 일단 조회가 안되어야 쓰겄는디

        userInfoResponseById(1).andExpect {
            status { isOk() }
        }.andDo { print() }
        userInfoResponseById(2).andExpect {
            status { isOk() }
        }.andDo { print() }
    }

    @Test
    @Transactional
    fun `user 정보 수정`() {
        modifyUser(name = "bomoonP",
            body = """
                {
                    "email": "bomoonP@gmail.com",
                    "name": "bomoonP",
                    "password": "bomoonP",
                    "university": "Rocket Univ.",
                    "accepted": "true"
                }
            """.trimIndent()
        ).andExpect { status { isOk() } }
            .andDo { print() }

        modifyUser(name = "bomoonI",
            body = """
                {
                    "email": "bomoonI@gmail.com",
                    "name": "bomoonI",
                    "password": "bomoonI",
                    "company": "BaeMin",
                    "year": "2"
                }
            """.trimIndent()
        ).andExpect { status { isOk() } }
            .andDo { print() }
    }


    @Test
    @Transactional
    fun `참여자 등록`(){
        registerParticipant(name = "bomoonI",
            body = """
                {
                    "university": "SNU",
                    "accepted": "true"
                }
            """.trimIndent()
        ).andExpect { status { isCreated() } }
            .andDo { print() }
    }

    @Test
    @Transactional
    fun `내 정보 조회`() {
        userMe("bomoonP").andExpect {
            status { isOk() }
        }.andDo { print() }

        userMe("bomoonI").andExpect {
            status { isOk() }
        }
    }

    @Test
    @Transactional
    fun `회원 가입 정상 동작 검증`() {
        signupAsParticipantUser("bomoonPother").andExpect {
            status { isNoContent() }
            header { exists("Authentication") }
        }
    }

    @Test
    @Transactional
    fun `회원 가입 시 중복`() {
        signupAsParticipantUser("bomoonP").andExpect {
            status { isConflict() }
        }
        signupAsInstructorUser("bomoonI").andExpect {
            status { isConflict() }
        }
    }

    @Test
    @Transactional
    fun `회원 가입시 request 값 오류`() {
        signup(
            """
                {
                    "email": "wrong_role@gmail.com",
                    "name": "wrong_role",
                    "password": "password",
                    "role": "wrong_role",
                    "university": "Test Univ."
                }
            """.trimIndent()
        ).andExpect { status { isBadRequest() } }

        signup(
            """
                {
                    "email": "no_role@gmail.com",
                    "name": "no_role",
                    "password": "password"
                }
            """.trimIndent()
        ).andExpect { status { isBadRequest() } }
    }

    @Test
    @Transactional
    fun `세미나 생성 검증`() {
        val request =
            """
                {
                    "name": "Seminar",
                    "capacity": 30,
                    "count": 4,
                    "time": "14:00"
                }
            """.trimIndent()
        createSeminar(name = "bomoonI", request
        )
            .andExpect {
                status { isCreated() } }
    }

    @Test
    @Transactional
    fun `participant는 세미나 생성 불가`() {
        createSeminar("bomoonP",
        """
            {
                "name": "bomoonP Seminar",
                "capacity": "40",
                "count": "5",
                "time": "12:00"
            }
        """.trimIndent()
        ).andExpect { status { isForbidden() } }
    }

    private fun signupAsParticipantUser(name: String): ResultActionsDsl {
        val body =
            """
                {
                    "email": "${name}@gmail.com",
                    "name": "${name}",
                    "password": "${name}",
                    "role": "participant",
                    "university": "Test Univ.."
                }
            """.trimIndent()
        return signup(body)
    }

    private fun signupAsInstructorUser(name: String): ResultActionsDsl {
        val body =
            """
                {
                    "email": "${name}@gmail.com",
                    "name": "${name}",
                    "password": "${name}",
                    "role": "instructor"
                }
            """.trimIndent()
        return signup(body)
    }

    private fun signup(body: String): ResultActionsDsl {
        return mockMvc.post("/api/v1/users/") {
            content = (body)
            contentType = (MediaType.APPLICATION_JSON)
            accept = (MediaType.APPLICATION_JSON)
        }
    }

    private fun signin(name: String): String? {
        return mockMvc.post("/api/v1/users/signin/") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content =
                """
                    {
                        "email": "${name}@gmail.com",
                        "password": "${name}"
                    }
                """.trimIndent()
        }.andReturn().response.getHeader("Authentication")
    }

    private fun userMe(name: String): ResultActionsDsl {
        val authentication = signin(name)
        return mockMvc.get("/api/v1/users/me/") {
            header("Authentication", authentication!!)
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun userInfoResponseById(id: Long): ResultActionsDsl {
        val user = userService.getUserResponseId(id)
        return mockMvc.get("/api/v1/users/{id}/", id) {
            header("Authentication", signin(user.name)!!)
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun modifyUser(name: String, body: String): ResultActionsDsl {
        val authentication = signin(name)
        return mockMvc.put("/api/v1/users/me/") {
            header("Authentication", authentication!!)
            content = body
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun registerParticipant(name: String, body: String): ResultActionsDsl {
        val authentication = signin(name)
        return mockMvc.post("/api/v1/users/participant/") {
            header("Authentication", authentication!!)
            content = body
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun createSeminar(name: String, body: String): ResultActionsDsl {
        val authentication = signin(name)
        return mockMvc.post("/api/v1/seminars/") {
            header("Authentication", authentication!!)
            content = body
            contentType = (MediaType.APPLICATION_JSON)
            accept = (MediaType.APPLICATION_JSON)
        }
    }
}
