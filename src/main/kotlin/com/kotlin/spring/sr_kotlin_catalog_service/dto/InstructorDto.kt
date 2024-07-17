package com.kotlin.spring.sr_kotlin_catalog_service.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDto(
    val id: Int?,
    @get:NotBlank(message = "instructorDto.name must not be blank")
    val name: String,
)