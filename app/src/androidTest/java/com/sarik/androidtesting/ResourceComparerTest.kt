package com.sarik.androidtesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Mehedi Hasan on 11/19/2020.
 */
class ResourceComparerTest {
    private lateinit var obj: ResourceComparer

    @Before
    fun setUp() {
        obj = ResourceComparer()
    }

    @After
    fun tearDown(){
        // Garbase collection auto collect obj, no need after for this scenario
    }

    @Test
    fun stringSameAsGiveString() { // should return true
        val context = ApplicationProvider.getApplicationContext<Context>()
        val resObj = obj.isEqual(context, R.string.app_name, "Android Testing")
        assertThat(resObj).isTrue()
    }

    @Test
    fun stringSameAsGiveStringReturnFalse() { // should return false
        val context = ApplicationProvider.getApplicationContext<Context>()
        val resObj = obj.isEqual(context, R.string.app_name, "Android")
        assertThat(resObj).isFalse()
    }
}