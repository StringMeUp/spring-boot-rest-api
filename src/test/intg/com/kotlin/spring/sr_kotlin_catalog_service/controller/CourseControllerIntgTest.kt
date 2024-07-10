package com.kotlin.spring.sr_kotlin_catalog_service.controller

import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import com.kotlin.spring.sr_kotlin_catalog_service.repository.CourseRepository
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseDTO
import com.kotlin.spring.sr_kotlin_catalog_service.util.courseEntityList
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var repository: CourseRepository

    @BeforeEach
    fun setUp() {
        val courses = courseEntityList()
        repository.deleteAll()
        repository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val courseDto = CourseDto(id = null, name = "Test Course", category = "IT")
        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
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
        val course = Course(id = null, name = "Course", category = "IT")
        repository.save(course)

        val updateDto = CourseDto(id = null, name = "Updated Course", category = "Update IT")

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

        webTestClient.delete()
            .uri("/v1/courses/{courseId}", courseId)
            .exchange()
            .expectStatus().isNoContent
    }
}