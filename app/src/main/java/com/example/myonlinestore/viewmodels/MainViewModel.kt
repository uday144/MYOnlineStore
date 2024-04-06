package com.example.myonlinestore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myonlinestore.models.Product
import com.example.myonlinestore.repositories.ProductRepository
import com.example.myonlinestore.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {


    private val _products = MutableStateFlow<NetworkResult<List<Product>>>(NetworkResult.Empty())
    val products: StateFlow<NetworkResult<List<Product>>>
        get() = _products

    fun fetchProducts(){
        viewModelScope.launch {
            repository.getProducts().collect {
                _products.value = it
            }
        }
    }
}