package com.example.myonlinestore.repositories

import com.example.myonlinestore.models.Product
import com.example.myonlinestore.network.StoreAPI
import com.example.myonlinestore.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RemoteProductRepositoryTest{

    @Mock
    lateinit var storeAPI: StoreAPI


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {
        Mockito.`when`(storeAPI.getProducts()).thenReturn(Response.success(emptyList()))

        val sut = RemoteProductRepository(storeAPI)
        val result = sut.getProducts()
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetProducts_expectedProductList() = runTest {
        val productList = listOf<Product>(
            Product("", "", 1, "", 40.3, "Prod 1"),
            Product("", "", 2, "", 20.2, "Prod 2")
        )
        Mockito.`when`(storeAPI.getProducts()).thenReturn(Response.success(productList))

        val sut = RemoteProductRepository(storeAPI)
        val result = sut.getProducts()
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(2, result.data!!.size)
        assertEquals("Prod 1", result.data!![0].title)
    }

    @Test
    fun testGetProducts_expectedError() = runTest {
        Mockito.`when`(storeAPI.getProducts()).thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val sut = RemoteProductRepository(storeAPI)
        val result = sut.getProducts()
        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong", result.message)
    }
}