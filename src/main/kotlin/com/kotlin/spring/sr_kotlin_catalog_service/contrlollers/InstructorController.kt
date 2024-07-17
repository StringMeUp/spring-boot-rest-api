package com.kotlin.spring.sr_kotlin_catalog_service.contrlollers

import com.kotlin.spring.sr_kotlin_catalog_service.dto.InstructorDto
import com.kotlin.spring.sr_kotlin_catalog_service.repository.InstructorRepository
import com.kotlin.spring.sr_kotlin_catalog_service.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instructors")
class InstructorController(val service: InstructorService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getInstructors(): List<InstructorDto> {
        return service.getAllInstructors()
    }
}