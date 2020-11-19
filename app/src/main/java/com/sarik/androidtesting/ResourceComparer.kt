package com.sarik.androidtesting

import android.content.Context

/**
 * Created by Mehedi Hasan on 11/19/2020.
 */
class ResourceComparer {
    fun isEqual(context: Context, resId: Int, string: String): Boolean{
        return context.getString(resId) == string
    }
}