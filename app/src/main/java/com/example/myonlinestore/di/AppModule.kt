package com.example.myonlinestore.di

import android.content.Context
import androidx.room.Room
import com.example.myonlinestore.db.StoreDAO
import com.example.myonlinestore.db.StoreDB
import com.example.myonlinestore.network.StoreAPI
import com.example.myonlinestore.repositories.LocalProductRepository
import com.example.myonlinestore.repositories.ProductRepository
import com.example.myonlinestore.repositories.RemoteProductRepository
import com.example.myonlinestore.utils.Constants
import com.example.myonlinestore.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)

        return retrofit.build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): StoreAPI {
        return retrofit.create(StoreAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideStoreDB(@ApplicationContext context : Context) : StoreDB {
        return Room.databaseBuilder(context, StoreDB::class.java, "StoreDB").build()
    }

}