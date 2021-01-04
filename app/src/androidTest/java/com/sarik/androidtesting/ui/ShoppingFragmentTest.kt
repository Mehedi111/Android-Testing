package com.sarik.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.sarik.androidtesting.R
import com.sarik.androidtesting.adapters.ShoppingItemAdapter
import com.sarik.androidtesting.data.local.ShoppingItem
import com.sarik.androidtesting.getOrAwaitValue
import com.sarik.androidtesting.lunchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_shopping.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/30/2020.
 */

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testShoppingFragmentFactory: TestShoppingFragmentFactory

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject() //this function inject all our dependency here which is annotate with @inject
    }

    @Test
    fun swipeShoppingItem_deleteInDb() {
        val shoppingItem = ShoppingItem("Test", 1, 1f, "TEST", 1)
        var testViewModel: ShoppingViewModel? = null
        lunchFragmentInHiltContainer<ShoppingFragment>(
            fragmentFactory = testShoppingFragmentFactory
        ) {
            testViewModel = this.viewModel
            viewModel?.insertShoppingItemToDB(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ImageViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItem_navigateToAddShoppingItemFragment() {
        val navController = Mockito.mock(NavController::class.java) //mock nav controller object
        lunchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(com.sarik.androidtesting.R.id.fabAddShoppingItem))
            .perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItem()
        )
    }


}