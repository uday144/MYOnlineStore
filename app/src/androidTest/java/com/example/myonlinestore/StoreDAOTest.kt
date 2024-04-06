package com.example.myonlinestore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myonlinestore.db.StoreDAO
import com.example.myonlinestore.db.StoreDB
import com.example.myonlinestore.models.Product
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class StoreDAOTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var storeDatabase: StoreDB
    private lateinit var StoreDAO: StoreDAO

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        StoreDAO = storeDatabase.getStoreDAO()
    }

    @Test
    fun insertProduct_returnsSingleProduct() = runTest {
        val product = Product("", "", 1, "", 12.33, "Test Product")
        StoreDAO.addProducts(listOf(product))
        val result = StoreDAO.getProducts()
        Assert.assertNotNull(result.first())
    }


    @After
    fun closeDatabase() {
        storeDatabase.close()
    }
}