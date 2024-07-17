package com.kotlin.spring.sr_kotlin_catalog_service.repository

import com.kotlin.spring.sr_kotlin_catalog_service.entity.Instructor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InstructorRepository : CrudRepository<Instructor, Int> {
}