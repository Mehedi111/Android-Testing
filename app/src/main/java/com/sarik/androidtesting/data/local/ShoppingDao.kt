package com.sarik.androidtesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Mehedi Hasan on 11/22/2020.
 */

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)


    @Query("SELECT * FROM Shopping_items")
    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observerTotalPrice():LiveData<Float>
}