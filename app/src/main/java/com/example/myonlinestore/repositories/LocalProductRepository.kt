package com.example.myonlinestore.repositories

import com.example.myonlinestore.db.StoreDAO
import com.example.myonlinestore.db.StoreDB
import com.example.myonlinestore.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalProductRepository @Inject constructor(private val storeDB: StoreDB) {

     fun getAllProducts(): Flow<List<Product>> {
        return storeDB.getStoreDAO().getProducts()
    }

    suspend fun insertProducts(products: List<Product>) {
        storeDB.getStoreDAO().addProducts(products)
    }
}