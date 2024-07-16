package com.kotlin.spring.sr_kotlin_catalog_service.contrlollers

import com.kotlin.spring.sr_kotlin_catalog_service.dto.CourseDto
import com.kotlin.spring.sr_kotlin_catalog_service.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(private val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDto: CourseDto): CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getCoursesByName(@RequestParam("course_name", required = false) courseName: String?): List<CourseDto> {
        return courseService.getCourses(courseName)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCourse(@RequestBody courseDto: CourseDto, @PathVariable("id") courseId: Int): CourseDto {
        return courseService.updateCourse(courseId, courseDto)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable courseId: Int) {
        courseService.deleteCourse(courseId)
    }
}