package com.kotlin.spring.sr_kotlin_catalog_service.service

import com.kotlin.spring.sr_kotlin_catalog_service.dto.InstructorDto
import com.kotlin.spring.sr_kotlin_catalog_service.repository.InstructorRepository
import org.springframework.stereotype.Service

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    fun getAllInstructors(): List<InstructorDto> {
       return instructorRepository.findAll().map { InstructorDto(it.Id, it.name, it.courses) }
    }
}
