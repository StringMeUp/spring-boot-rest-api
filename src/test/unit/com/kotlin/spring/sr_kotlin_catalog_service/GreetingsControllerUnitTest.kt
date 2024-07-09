package com.kotlin.spring.sr_kotlin_catalog_service

import com.kotlin.spring.sr_kotlin_catalog_service.contrlollers.GreetingsController
import com.kotlin.spring.sr_kotlin_catalog_service.service.GreetingsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingsController::class])
@AutoConfigureWebTestClient
class GreetingsControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingsServiceMock: GreetingsService

    @Test
    fun retrieveGreeting() {
        val name = "Samir"

        every { greetingsServiceMock.retrieveGreetings(any()) } returns "Hello, $name!, Hello from test profile"

        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()
        Assertions.assertEquals("Hello, $name!, Hello from test profile", result.responseBody)
    }

}