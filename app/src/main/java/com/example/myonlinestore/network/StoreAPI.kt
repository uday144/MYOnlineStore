package com.example.myonlinestore.network

import com.example.myonlinestore.models.Product
import retrofit2.http.GET

interface StoreAPI {

    @GET("products")
    suspend fun getProducts() : List<Product>
}