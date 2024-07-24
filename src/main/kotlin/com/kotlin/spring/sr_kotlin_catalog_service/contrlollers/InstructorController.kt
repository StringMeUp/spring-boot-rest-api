package com.kotlin.spring.sr_kotlin_catalog_service.contrlollers

import com.kotlin.spring.sr_kotlin_catalog_service.dto.InstructorDto
import com.kotlin.spring.sr_kotlin_catalog_service.service.InstructorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(val service: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addInstructors(@RequestBody @Valid instructorDto: InstructorDto): InstructorDto {
        return service.addInstructor(instructorDto)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getInstructors(): List<InstructorDto> {
        return service.getAllInstructors()
    }
}