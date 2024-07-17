package com.kotlin.spring.sr_kotlin_catalog_service.entity

import jakarta.persistence.*

@Entity
@Table(name = "INSTRUCTORS")
data class Instructor(
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    val Id: Int?,
    val name: String,
    @OneToOne(
        targetEntity = Course::class,
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val courses: List<Course> = mutableListOf()
)