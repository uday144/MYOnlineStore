package com.example.myonlinestore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myonlinestore.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products : List<Product>)

    @Query("SELECT * FROM Product")
    fun getProducts() : Flow<List<Product>>

}