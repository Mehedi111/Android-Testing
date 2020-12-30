package com.sarik.androidtesting.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sarik.androidtesting.R
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */
class AddShoppingItem: Fragment(R.layout.fragment_add_shopping_item) {
    private lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(AddShoppingItemDirections.actionAddShoppingItemToImagePickFragment())
        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}