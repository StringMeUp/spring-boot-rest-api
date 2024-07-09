package com.kotlin.spring.sr_kotlin_catalog_service

import com.kotlin.spring.sr_kotlin_catalog_service.contrlollers.CourseController
import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.service.CourseService
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {

        every { courseServiceMock.addCourse(any()) } returns  courseDTO().copy(id = 2)

        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue( courseDTO())
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(savedCourseDto!!.id)
        assertEquals("Test Course", savedCourseDto.name)
        assertEquals("IT", savedCourseDto.category)
    }

}