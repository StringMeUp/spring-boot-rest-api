package com.kotlin.spring.sr_kotlin_catalog_service.repository

import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int>{}
