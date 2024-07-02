package com.kotlin.spring.sr_kotlin_catalog_service.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingsService {

    @Value("\${message}")
    lateinit var message: String

    fun retrieveGreetings(name: String): String {
        return "Hello, $name!, $message"
    }
}