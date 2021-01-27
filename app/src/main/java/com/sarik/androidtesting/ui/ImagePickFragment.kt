package com.sarik.androidtesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sarik.androidtesting.R
import com.sarik.androidtesting.adapters.ImageAdapter
import com.sarik.androidtesting.others.Constants
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
): Fragment(R.layout.fragment_image_pick) {
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        setUpRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)
        }


    }

    private fun setUpRecyclerView(){
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), Constants.GRIND_SPAN_COUNT)
        }
    }
}