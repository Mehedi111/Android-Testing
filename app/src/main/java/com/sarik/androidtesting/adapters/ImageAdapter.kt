package com.sarik.androidtesting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sarik.androidtesting.R
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/30/2020.
 */
class ImageAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var images: List<String>

        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)

        )

    }

    private var onItemClick : ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClick = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]

        holder.itemView.apply {
           try {
               glide.load(url).into(ivShoppingImage)
           } catch (e: Exception) {
           }

            setOnClickListener {
                onItemClick?.let { click ->
                    click(url)
                }
            }
        }





       /* setOnItemClickListener(::s)

        fun emp(s: String){

        }
        setOnItemClickListener {

        }*/

    }

    fun s(s: String): Unit{
        //here will print values
    }

    override fun getItemCount(): Int {
        return images.size
    }
}