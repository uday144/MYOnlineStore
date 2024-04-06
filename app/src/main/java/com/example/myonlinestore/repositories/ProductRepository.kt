package com.example.myonlinestore.repositories

import com.example.myonlinestore.models.Product
import com.example.myonlinestore.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localRepository: LocalProductRepository,
    private val remoteRepository: RemoteProductRepository
) {
    fun getProducts(): Flow<NetworkResult<List<Product>>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                val remoteProducts = remoteRepository.getProducts()
                if (remoteProducts is NetworkResult.Success) {
                    remoteProducts.data?.let { localRepository.insertProducts(it) }
                    emit(remoteProducts)
                } else {
                    val localProducts = localRepository.getAllProducts().firstOrNull()
                    if (!localProducts.isNullOrEmpty()) {
                        emit(NetworkResult.Success(localProducts))
                    } else {
                        emit(NetworkResult.Error("No data available"))
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An error occurred"))
            }
        }
    }
}