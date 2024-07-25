package com.kotlin.spring.sr_kotlin_catalog_service.exceptiopnhandler

import com.kotlin.spring.sr_kotlin_catalog_service.exception.InstructorNotValidException
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@ControllerAdvice
class GlobalExceptionHandlder : ResponseEntityExceptionHandler() {

    companion object : KLogging()

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("MethodArgumentNotValidException observed: ${ex.message}", ex)


        val errors = ex.bindingResult.allErrors
            .map { error -> error.defaultMessage!! }
            .sorted()
            .joinToString(", ") { it }

        logger.info("Error info: $errors")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(InstructorNotValidException::class)
    fun handleInstructorNotValidException(exception: InstructorNotValidException, webRequest: WebRequest): ResponseEntity<Any> {
        logger.error("Exception observed : ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(exception.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception, webRequest: WebRequest): ResponseEntity<Any> {
        logger.error("Exception observed : ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(exception.message)
    }
}