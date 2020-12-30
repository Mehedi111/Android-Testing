package com.sarik.androidtesting

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Mehedi Hasan on 11/26/2020.
 */

/*If you create an inline function with a reified T,
* the type of T can be accessed even at runtime, and thus you do not need to pass the Class<T> additionally.
You can work with T as if it was a normal class - e.g.
you might want to check whether a variable is an instance of T, which you can easily do then: myVar is T.*/

/*
* use @crossinline keyword when a lamda function remain inside a inline function
* */
@ExperimentalCoroutinesApi
inline fun <reified T: Fragment> lunchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
){
    // we have no main activity in test scenario so intent will proivde us main activity reference
    // and move to test activity
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)

    /**
     * this will lunch @see HiltTestActivity
     */
    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = fragmentFactory
        }

        // now we created fragment
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )

        // set argument to fragment
        fragment.arguments = fragmentArgs

        // add fragment to activity
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment)
            .commitNow()

        // call lambda function
        (fragment as T).action()
    }
}
