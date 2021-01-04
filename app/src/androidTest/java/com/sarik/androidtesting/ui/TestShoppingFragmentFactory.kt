package com.sarik.androidtesting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.sarik.androidtesting.adapters.ImageAdapter
import com.sarik.androidtesting.adapters.ShoppingItemAdapter
import com.sarik.androidtesting.repositories.FakseShoppingRepoAndroidTRest
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/31/2020.
 */
class TestShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItem::class.java.name -> AddShoppingItem(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(shoppingItemAdapter, ShoppingViewModel(
                FakseShoppingRepoAndroidTRest()
            )
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}