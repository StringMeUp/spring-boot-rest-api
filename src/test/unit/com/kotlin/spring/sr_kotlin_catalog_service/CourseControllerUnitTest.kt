package com.kotlin.spring.sr_kotlin_catalog_service

import com.kotlin.spring.sr_kotlin_catalog_service.contrlollers.CourseController
import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import com.kotlin.spring.sr_kotlin_catalog_service.service.CourseService
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseDTO
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseEntityList
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions
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

        every { courseServiceMock.addCourse(any()) } returns courseDTO().copy(id = 1)

        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO())
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(savedCourseDto!!.id)
        assertEquals("Test Course", savedCourseDto.name)
        assertEquals("IT", savedCourseDto.category)
    }

    @Test
    fun getCourses() {

        every { courseServiceMock.getCourses() }.returnsMany(courseEntityList().map {
            CourseDto(
                it.id,
                it.name,
                it.category
            )
        })

        val courses = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(courses)
        Assertions.assertIterableEquals(
            courses,
            courseEntityList().map { CourseDto(id = it.id, name = it.name, category = it.category) })
    }


    @Test
    fun updateCourse() {
        val course = Course(id = 100, name = "Course", category = "IT")
        val updateDto = CourseDto(id = 100, name = "Updated Course", category = "Update IT")

        every { courseServiceMock.updateCourse(any(), any()) }.returns(updateDto)

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{id}", course.id)
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(updatedCourse?.name, updateDto.name)
        assertEquals(updatedCourse?.category, updateDto.category)
    }
}