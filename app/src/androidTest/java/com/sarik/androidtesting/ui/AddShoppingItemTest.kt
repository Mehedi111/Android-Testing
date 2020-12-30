package com.sarik.androidtesting.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.sarik.androidtesting.R
import com.sarik.androidtesting.lunchFragmentInHiltContainer
import com.sarik.androidtesting.repositories.ShoppingRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Created by Mehedi Hasan on 12/30/2020.
 */

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
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
    fun clickShoppingImage_navigateImagePickFragment(){
        val navController = mock(NavController::class.java)
        lunchFragmentInHiltContainer<AddShoppingItem> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.ivShoppingImage)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            AddShoppingItemDirections.actionAddShoppingItemToImagePickFragment()
        )
    }

    /*@Test
    fun pressBack_makeImageUrlEmpty(){
        val navController = mock(NavController::class.java)
        lunchFragmentInHiltContainer<AddShoppingItem> {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

        val viewModel = Mockito.mock(ShoppingViewModel::class.java)
        Mockito.verify(viewModel).setCurrentImageUrl("")
    }*/
}