package com.example.myonlinestore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myonlinestore.models.Product

@Database(entities = [Product::class], version = 1)
abstract class StoreDB : RoomDatabase() {

    abstract fun getStoreDAO() : StoreDAO

}