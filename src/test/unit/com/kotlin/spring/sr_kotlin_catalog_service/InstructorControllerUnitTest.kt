package com.kotlin.spring.sr_kotlin_catalog_service

import com.kotlin.spring.sr_kotlin_catalog_service.contrlollers.CourseController
import com.kotlin.spring.sr_kotlin_catalog_service.contrlollers.InstructorController
import com.kotlin.spring.sr_kotlin_catalog_service.dto.InstructorDto
import com.kotlin.spring.sr_kotlin_catalog_service.repository.InstructorRepository
import com.kotlin.spring.sr_kotlin_catalog_service.service.InstructorService
import com.kotlin.spring.sr_kotlin_catalog_service.util.instructorDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorService

    @Test
    fun addInstructors() {
        every { instructorServiceMock.addInstructor(any()) } returns instructorDTO().copy(id = 100)

        val instructorDto = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO())
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult().responseBody

        assertNotNull(instructorDto?.id)
        assertEquals(instructorDTO().name, instructorDto?.name)
    }
}