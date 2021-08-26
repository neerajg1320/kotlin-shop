package com.example.imageassetdemo.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R

import com.example.imageassetdemo.models.SoldProduct
import com.example.imageassetdemo.ui.activities.SoldProductDetailsActivity
import com.example.imageassetdemo.ui.custom.CustomTextViewBold
import com.example.imageassetdemo.Constants
import com.example.imageassetdemo.databinding.ItemListLayoutBinding
import com.example.imageassetdemo.ui.custom.GlideLoader


/**
 * A adapter class for sold products list items.
 */
open class SoldProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<SoldProduct>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    // class SoldProductViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class SoldProductViewHolder(val binding: ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemListLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SoldProductViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as SoldProductViewHolder) {
            with(list[position]) {
                GlideLoader(context).loadProductPicture(
                    this.image,
                    binding.ivItemImage
                )

                binding.tvItemName.text = this.title
                binding.tvItemPrice.text = "$${this.price}"

                binding.ibDeleteProduct.visibility = View.GONE

                holder.itemView.setOnClickListener {
                    val intent = Intent(context, SoldProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS, this)
                    context.startActivity(intent)
                }
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }


}