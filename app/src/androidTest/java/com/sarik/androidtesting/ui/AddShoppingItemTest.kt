package com.sarik.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.sarik.androidtesting.R
import com.sarik.androidtesting.data.local.ShoppingItem
import com.sarik.androidtesting.getOrAwaitValue
import com.sarik.androidtesting.lunchFragmentInHiltContainer
import com.sarik.androidtesting.repositories.FakseShoppingRepoAndroidTRest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/30/2020.
 */

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun pressBack_popBackStack() {
        val navController = mock(NavController::class.java)
        lunchFragmentInHiltContainer<AddShoppingItem> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDB() {
        val testViewModel = ShoppingViewModel(FakseShoppingRepoAndroidTRest())
        lunchFragmentInHiltContainer<AddShoppingItem>(
            fragmentFactory = fragmentFactory
        ) {
            this.viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())


        assertThat(
            testViewModel.shoppingItems.getOrAwaitValue()
                .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
        )
    }

    @Test
    fun clickShoppingImage_navigateImagePickFragment() {
        val navController = mock(NavController::class.java)
        lunchFragmentInHiltContainer<AddShoppingItem> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.ivShoppingImage)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            AddShoppingItemDirections.actionAddShoppingItemToImagePickFragment()
        )
    }


}