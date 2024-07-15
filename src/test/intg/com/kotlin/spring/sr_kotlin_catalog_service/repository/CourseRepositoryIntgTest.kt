package com.kotlin.spring.sr_kotlin_catalog_service.repository

import com.kotlin.spring.sr_kotlin_catalog_service.util.courseEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var repository: CourseRepository

    @BeforeEach
    fun setUp() {
        val courses = courseEntityList()
        repository.deleteAll()
        repository.saveAll(courses)
    }

    @Test
    fun findByNameContaining_test() {
        val result = repository.findByNameContaining("B")
        assertEquals(1, result.size)
    }

    @Test
    fun findByName_test() {
        val result = repository.findByName("Tes")
        assertEquals(3, result.size)
    }
}