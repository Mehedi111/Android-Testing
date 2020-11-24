package com.sarik.androidtesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sarik.androidtesting.R

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */
class AddShoppingItem: Fragment(R.layout.fragment_add_shopping_item) {
    private lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }
}