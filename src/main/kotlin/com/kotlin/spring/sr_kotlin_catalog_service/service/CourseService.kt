package com.kotlin.spring.sr_kotlin_catalog_service.service

import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Course
import com.kotlin.spring.sr_kotlin_catalog_service.entity.Instructor
import com.kotlin.spring.sr_kotlin_catalog_service.exception.CourseNotFoundException
import com.kotlin.spring.sr_kotlin_catalog_service.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseRepository: CourseRepository) {

    companion object : KLogging()

    /** Add a course */
    fun addCourse(courseDTO: CourseDto): CourseDto {
        val courseEntity = courseDTO.let {
            Course(it.id, it.name, it.category, Instructor(1, "Samir"))
        }

        logger.info("Created new course with id $courseEntity")
        courseRepository.save(courseEntity)

        return CourseDto(courseEntity.id, courseDTO.name, courseDTO.category, courseDTO.instructorId)
    }

    /** Get all course */
    fun getCourses(courseName: String?): List<CourseDto> {
        val courses = courseName?.let {
            courseRepository.findByNameContaining(it)
        } ?: courseRepository.findAll()

        //TODO Check string representation -> logger.info("Getting all courses $courses")
        return courses.map { CourseDto(it.id, it.name, it.category, 1) }
    }

    /** Update a course */
    fun updateCourse(id: Int, course: CourseDto): CourseDto {
        val existingCourse = courseRepository.findById(id)

        return if (existingCourse.isPresent) {
            existingCourse.get().let {
                val updatedCourse = it.copy(name = course.name, category = course.category)
                courseRepository.save(updatedCourse)
                CourseDto(updatedCourse.id, updatedCourse.name, updatedCourse.category, updatedCourse.instructor?.id!!)
            }
        } else {
            throw CourseNotFoundException("Course with id $id not found")
        }
    }

    /** Delete a course by id */
    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)
        if (existingCourse.isPresent) {
            existingCourse.get().let {
                courseRepository.delete(it)
            }
        } else {
            throw CourseNotFoundException("Course deletion by id $courseId not found")
        }
    }
}
