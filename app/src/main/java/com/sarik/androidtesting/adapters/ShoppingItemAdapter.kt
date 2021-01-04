package com.sarik.androidtesting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sarik.androidtesting.R
import com.sarik.androidtesting.data.local.ShoppingItem
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/30/2020.
 */
class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping, parent, false)

        )
    }

    private var onItemClick : ((ShoppingItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ShoppingItem) -> Unit){
        onItemClick = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = shoppingItems[position]

        holder.itemView.apply {
           try {
               glide.load(item.imageUrl).into(ivShoppingImage)
           } catch (e: Exception) {
           }

            setOnClickListener {
                onItemClick?.let { click ->
                    click(item)
                }
            }
        }



    }


    override fun getItemCount(): Int {
        return shoppingItems.size
    }
}