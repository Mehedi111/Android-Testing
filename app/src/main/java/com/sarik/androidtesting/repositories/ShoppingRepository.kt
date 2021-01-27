package com.sarik.androidtesting.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.sarik.androidtesting.others.Resource
import com.sarik.androidtesting.data.local.ShoppingItem

/**
 * Created by Mehedi Hasan on 11/23/2020.
 */
interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(string: String): Resource<ImageResponse>
}