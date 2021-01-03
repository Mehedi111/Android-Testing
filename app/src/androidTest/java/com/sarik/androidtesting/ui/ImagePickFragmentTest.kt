package com.sarik.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.sarik.androidtesting.R
import com.sarik.androidtesting.adapters.ImageAdapter
import com.sarik.androidtesting.getOrAwaitValue
import com.sarik.androidtesting.lunchFragmentInHiltContainer
import com.sarik.androidtesting.repositories.FakseShoppingRepoAndroidTRest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/31/2020.
 */

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest{


    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // this rule need to declare to test live data


    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        //lunchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory, action = ::getImagePickFragment)
        val navController = mock(NavController::class.java)
        val imageUrl = "https://image.tmdb.org/t/p/w92/dRLSoufWtc16F5fliK4ECIVs56p.jpg"
        val testViewModel = ShoppingViewModel(FakseShoppingRepoAndroidTRest())
        lunchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            this.imageAdapter.images = listOf(imageUrl)
            this.viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0, ViewActions.click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.curImageUrl.getOrAwaitValue()).isEqualTo(imageUrl)

    }

/*    fun getImagePickFragment(imagePickFragment: ImagePickFragment){

    }*/
}