package com.sarik.androidtesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Mehedi Hasan on 11/22/2020.
 */

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun getShoppingDao(): ShoppingDao
}