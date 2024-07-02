package com.kotlin.spring.sr_kotlin_catalog_service.contrlollers

import com.kotlin.spring.sr_kotlin_catalog_service.service.ProductsService
import mu.KLogging
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/products")
class ProductsController(val service: ProductsService) {

    companion object : KLogging()

    @GetMapping
    fun retrieveGreetings(@RequestParam("id") id: String): List<String> {
        logger.info("Id is: $id")
        return service.getAllProducts(id)
    }
}