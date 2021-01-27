package com.sarik.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sarik.androidtesting.others.Status
import com.google.common.truth.Truth.assertThat
import com.sarik.androidtesting.MainCoroutineRule
import com.sarik.androidtesting.getOrAwaitValueTest
import com.sarik.androidtesting.others.Constants
import com.sarik.androidtesting.repositories.FakeShoppingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // use custom coroutine rule for test purposes

    private lateinit var shoppingViewModel: ShoppingViewModel

    @Before
    fun before() {
        shoppingViewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field should return error`() {
        shoppingViewModel.insertShoppingItem("name", "", "3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert shopping item with too long name should return error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(string, "5", "3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price should return error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem("name", "5", string)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount should return error`() {

        shoppingViewModel.insertShoppingItem("name", "999999999999999999999999", "9.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input should return success`() {

        shoppingViewModel.insertShoppingItem("name", "33", "9.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}