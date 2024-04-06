package com.example.myonlinestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myonlinestore.adapter.ProductAdapter
import com.example.myonlinestore.databinding.ActivityMainBinding
import com.example.myonlinestore.utils.NetworkResult
import com.example.myonlinestore.viewmodels.MainViewModel
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter()

        binding.productList.layoutManager = LinearLayoutManager(this)
        binding.productList.adapter = adapter

        Timber.i("Hi, I am Main Class")

        lifecycleScope.launch {
            viewModel.products.collect { resource ->
                when (resource) {
                    is NetworkResult.Success -> {
                        adapter = resource.data?.let { ProductAdapter(it) }!!
                        Timber.i("Hi, Success")
                    }

                    is NetworkResult.Error -> {
                        // Handle error
                        Timber.i("Hi, Error")
                    }

                    is NetworkResult.Loading -> {
                        // Show loading progress
                        Timber.i("Hi, Loading")
                    }

                    else -> {}
                }
            }
        }

         viewModel.fetchProducts()
    }
}