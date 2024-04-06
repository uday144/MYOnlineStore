package com.example.myonlinestore.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.bumptech.glide.load.engine.Resource
import com.example.myonlinestore.models.Product
import com.example.myonlinestore.repositories.ProductRepository
import com.example.myonlinestore.utils.NetworkResult
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestViewModelScopeRule()

    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `fetchProducts success`() = runTest {
        // Given
        val products = listOf(Product("", "", 1, "", 12.33, "Test Product"))
        Mockito.`when`(repository.getProducts()).thenReturn(flowOf(NetworkResult.Success(products)))


        viewModel.products.test {
           val product = awaitItem()
            assertEquals(1, product.data?.size)
            cancel()
        }

        // When
        verify( viewModel).fetchProducts()
    }
}