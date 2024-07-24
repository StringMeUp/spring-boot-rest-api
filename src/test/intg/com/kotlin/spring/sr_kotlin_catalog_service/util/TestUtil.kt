package com.kotlin.spring.sr_kotlin_catalog_service.util

import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Instructor

fun courseEntityList(): List<Course> {
    return listOf(
        Course(1, "Bingo", "IT"),
        Course(2, "Test 666", "Administration"),
        Course(3, "Test 68", "Administration"),
        Course(4, "Test 9", "Administration"),
    )
}

fun instructorEntityList(): List<Instructor> {
    return listOf(
        Instructor(1, "Ins Test 1",),
        Instructor(2, "Ins Test 666"),
        Instructor(3, "Ins Test 68"),
        Instructor(4, "Ins Test 9"),
    )
}

fun courseDTO(
    id: Int? = null,
    name: String = "Test Course", category: String = "IT"
) = CourseDto(id, name, category)
