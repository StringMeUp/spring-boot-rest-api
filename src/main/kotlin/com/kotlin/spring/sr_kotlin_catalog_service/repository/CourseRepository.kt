package com.kotlin.spring.sr_kotlin_catalog_service.repository

import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CourseRepository : CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String): List<Course>

    @Query(value = "SELECT * FROM Courses WHERE name like %:courseName%", nativeQuery = true)
    fun findByName(@Param("courseName") courseName: String): List<Course>
}
