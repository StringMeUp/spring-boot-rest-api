package com.kotlin.spring.sr_kotlin_catalog_service.contrlollers

import com.kotlin.spring.sr_kotlin_catalog_service.service.GreetingsService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingsController(private val greetingsService: GreetingsService) {

    companion object : KLogging()

    @GetMapping("/{name}")
    fun retrieveGreetings(@PathVariable name: String): String {
        logger.info("Name is: $name")
        return greetingsService.retrieveGreetings(name)
    }
}