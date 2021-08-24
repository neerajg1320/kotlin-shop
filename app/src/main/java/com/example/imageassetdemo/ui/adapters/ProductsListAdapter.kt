package com.example.imageassetdemo.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.models.Product
import com.example.imageassetdemo.ui.fragments.ProductsFragment
import com.example.imageassetdemo.util.GlideLoader


/**
 * A adapter class for products list items.
 */
open class ProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("ProductsListAdapter", "onCreate()")
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
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
        Log.d("ProductsListAdapter", "onBindViewHolder(): list.size = ${list.size}")
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(model.image,
                holder.itemView.findViewById(R.id.iv_item_image))

            holder.itemView.findViewById<TextView>(R.id.tv_item_name).text = model.title
            holder.itemView.findViewById<TextView>(R.id.tv_item_price).text = "$${model.price}"

            holder.itemView.findViewById<ImageButton>(R.id.ib_delete_product).setOnClickListener {

                fragment.deleteProduct(model.product_id)
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        Log.d("ProductsListAdapter", "list.size = ${list.size}")
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}