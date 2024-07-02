package com.kotlin.spring.sr_kotlin_catalog_service.service

import org.springframework.stereotype.Service

@Service
class ProductsService {
    fun getAllProducts(id: String) = listOf("Samir", "Ramic", "Products with id: $id")
}