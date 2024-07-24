package com.kotlin.spring.sr_kotlin_catalog_service

import com.kotlin.spring.sr_kotlin_catalog_service.contrlollers.CourseController
import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import com.kotlin.spring.sr_kotlin_catalog_service.service.CourseService
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseDTO
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseEntityList
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
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
    fun addCourse_Validation() {
        val courseDto = courseDTO(name = "", category = "")

        val result = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("courseDto.category must not be blank, courseDto.name must not be blank", result)
    }

    @Test
    fun getCourses() {

        every { courseServiceMock.getCourses(any()) }.returnsMany(courseEntityList().map {
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

    @Test
    fun deleteCourse() {
        val courseId = courseEntityList().map { CourseDto(it.id, it.name, it.category) }.first().id ?: 1

        every { courseServiceMock.deleteCourse(any()) } just runs
        webTestClient.delete()
            .uri("/v1/courses/{courseId}", courseId)
            .exchange()
            .expectStatus().isNoContent
    }
}