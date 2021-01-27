package com.sarik.androidtesting.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sarik.androidtesting.others.Status
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.sarik.androidtesting.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */
class AddShoppingItem @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        subscribeToObservers()

        btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemPrice.text.toString(),
                etShoppingItemAmount.text.toString()
            )
        }

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(AddShoppingItemDirections.actionAddShoppingItemToImagePickFragment())
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers() {
        viewModel.curImageUrl.observe(viewLifecycleOwner) {
            try {
                glide.load(it).into(ivShoppingImage)
            } catch (e: Exception) {
            }
        }

        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            "Sucessfully inserted",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            it.message ?: "An unknown error occured !",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> {
                        /*NO OP*/
                    }
                }
            }
        })

    }
}