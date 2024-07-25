package com.kotlin.spring.sr_kotlin_catalog_service.service

import com.kotlin.spring.sr_kotlin_catalog_service.dto.InstructorDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Instructor
import com.kotlin.spring.sr_kotlin_catalog_service.repository.InstructorRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(private val instructorRepository: InstructorRepository) {

    companion object : KLogging()

    fun addInstructor(instructorDto: InstructorDto): InstructorDto {
        val instructor = instructorDto.let {
            Instructor(id = null, name = it.name)
        }

        logger.info("Adding instructor: $instructor")
        instructorRepository.save(instructor)
        return InstructorDto(instructor.id, instructor.name)
    }

    fun getAllInstructors(): List<InstructorDto> {
        return instructorRepository.findAll().map { InstructorDto(it.id, it.name) }
    }

    fun findByInstructorId(id: Int): Optional<InstructorDto> {
        return instructorRepository.findById(id).map { InstructorDto(it.id, it.name) }
    }
}
