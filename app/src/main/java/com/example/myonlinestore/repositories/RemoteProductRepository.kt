package com.example.myonlinestore.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myonlinestore.models.Product
import com.example.myonlinestore.network.SafeApiCall
import com.example.myonlinestore.network.StoreAPI
import com.example.myonlinestore.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteProductRepository @Inject constructor(private val storeAPI: StoreAPI) : SafeApiCall() {

    suspend fun getProducts() : NetworkResult<List<Product>> {
        return safeApiCall {
            storeAPI.getProducts()
        }
    }

}