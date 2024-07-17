package com.kotlin.spring.sr_kotlin_catalog_service.dto

import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import jakarta.validation.constraints.NotBlank

data class InstructorDto(
    val Id: Int?,
    @get:NotBlank(message = "instructorDto.name must not be blank")
    val name: String,
    val courses: List<Course>,
)