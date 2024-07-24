package com.kotlin.spring.sr_kotlin_catalog_service.controller

import com.kotlin.spring.sr_kotlin_catalog_service.dto.InstructorDto
import com.kotlin.spring.sr_kotlin_catalog_service.repository.InstructorRepository
import com.kotlin.spring.sr_kotlin_catalog_service.util.instructorEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var repository: InstructorRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
        repository.saveAll(instructorEntityList())
    }

    @Test
    fun addInstructor_Validation() {
        val instructor = InstructorDto(id = null, name = "")
        val result = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructor)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult().responseBody

        Assertions.assertEquals("instructorDto.name must not be blank", result)
    }

    @Test
    fun addInstructor() {
        val instructor = InstructorDto(id = null, name = "T-Instructor")
        val instructorDto = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructor)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult().responseBody

        assertNotNull(instructorDto?.id)
        assertEquals(instructor.name, instructorDto?.name)
    }


    @Test
    fun getInstructors() {
        val instructors = instructorEntityList()
        val instructorsDto = webTestClient.get()
            .uri("/v1/instructors")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(InstructorDto::class.java)
            .returnResult().responseBody

        Assertions.assertEquals(instructors.size, instructorsDto?.size)

    }
}