package com.sarik.androidtesting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mehedi Hasan on 11/22/2020.
 */

@Entity(tableName = "Shopping_items")
data class ShoppingItem(
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)