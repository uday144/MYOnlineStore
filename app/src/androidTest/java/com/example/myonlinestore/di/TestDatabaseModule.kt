package com.example.myonlinestore.di

import android.content.Context
import androidx.room.Room
import com.example.myonlinestore.db.StoreDB
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideTestDB(@ApplicationContext context: Context): StoreDB {
        return Room.inMemoryDatabaseBuilder(
            context,
            StoreDB::class.java
        ).allowMainThreadQueries().build()
    }
}