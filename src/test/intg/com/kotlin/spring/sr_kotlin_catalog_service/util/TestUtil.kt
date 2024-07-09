package com.kotlin.spring.sr_kotlin_catalog_service.util

import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course

fun courseEntityList(): List<Course> {
    return listOf(
        Course(1, "Test 1", "IT"),
        Course(2, "Test 666", "Administration")
    )
}

fun courseDTO(
    id: Int? = null,
    name: String = "Test Course", category: String = "IT"
) = CourseDto(id, name, category)