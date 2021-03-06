package com.sarik.androidtesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sarik.androidtesting.getOrAwaitValue
import com.sarik.androidtesting.lunchFragmentInHiltContainer
import com.sarik.androidtesting.ui.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Mehedi Hasan on 11/22/2020.
 */

@ExperimentalCoroutinesApi
@SmallTest // tell junit that it is unit test
@HiltAndroidTest //inject dependency into this class
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule //tell junit to run synchronous (One function after another)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao;

    @Before
    fun setup() {
        hiltRule.inject() //this function inject all our dependency here which is annotate with @inject
        dao = database.getShoppingDao()
    }

    @After // close db after complete
    fun dismiss() {
        database.close()
    }



    // Now test all the functionality of Dao

    @Test
    // Run blocking -> coroutine will runn on main thread in test purpose
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 2f, "url", 1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)

    }

    // Delete item test
    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 2f, "url", 1)

        dao.insertShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)

        dao.deleteShoppingItem(shoppingItem)
        val allShoppingItemTwo = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItemTwo).doesNotContain(shoppingItem)

    }

    //total price check test
    @Test
    fun getTotalPrice() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 3, 2f, "url", 1)
        val shoppingItemTwo = ShoppingItem("name", 4, 3.5f, "url", 2)
        val shoppingItemThree = ShoppingItem("name", 0, 100f, "url", 3)

        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItemTwo)
        dao.insertShoppingItem(shoppingItemThree)

        val allShoppingItem = dao.observerTotalPrice().getOrAwaitValue()
        assertThat(allShoppingItem).isEqualTo(3 * 2f + 4 * 3.5f)
    }

}