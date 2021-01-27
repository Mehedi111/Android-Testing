package com.sarik.androidtesting.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.sarik.androidtesting.others.Resource
import com.sarik.androidtesting.data.local.ShoppingDao
import com.sarik.androidtesting.data.local.ShoppingItem
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 11/23/2020.
 */
class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI

) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observerTotalPrice()
    }

    override suspend fun searchForImage(string: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(string)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Unknown error", null)
            } else {
                Resource.error("Unsuccessful", null)
            }
        } catch (e: Exception) {
            Resource.error("Error occurred !", null)
        }
    }
}