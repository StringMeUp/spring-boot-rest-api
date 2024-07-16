package com.kotlin.spring.sr_kotlin_catalog_service.repository

import com.kotlin.spring.sr_kotlin_catalog_service.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream
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

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findByNameTest_parametrized(name: String, expectedSize: Int) {
        val courses = repository.findByName(name)
        println("Courses $courses")

        Assertions.assertEquals(expectedSize, courses.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.arguments("Test", 3), Arguments.arguments("Bin", 1))
        }
    }
}