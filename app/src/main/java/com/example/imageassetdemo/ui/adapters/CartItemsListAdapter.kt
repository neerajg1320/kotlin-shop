package com.example.imageassetdemo.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.models.CartItem
import com.example.imageassetdemo.util.BoldTextView
import com.example.imageassetdemo.util.CustomTextView
import com.example.imageassetdemo.util.GlideLoader

/**
 * A adapter class for dashboard items list.
 */
open class CartItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<CartItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CartItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is CartItemViewHolder) {

            GlideLoader(context).loadProductPicture(model.image, holder.itemView.findViewById<ImageView>(R.id.iv_cart_item_image))

            holder.itemView.findViewById<CustomTextView>(R.id.tv_cart_item_title).text = model.title
            holder.itemView.findViewById<BoldTextView>(R.id.tv_cart_item_price).text = "$${model.price}"
            holder.itemView.findViewById<CustomTextView>(R.id.tv_cart_quantity).text = model.cart_quantity

//            if (model.cart_quantity == "0") {
//                holder.itemView.ib_remove_cart_item.visibility = View.GONE
//                holder.itemView.ib_add_cart_item.visibility = View.GONE
//
//                holder.itemView.tv_cart_quantity.text =
//                    context.resources.getString(R.string.lbl_out_of_stock)
//
//                holder.itemView.tv_cart_quantity.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.colorSnackBarError
//                    )
//                )
//            } else {
//                holder.itemView.ib_remove_cart_item.visibility = View.VISIBLE
//                holder.itemView.ib_add_cart_item.visibility = View.VISIBLE
//
//                holder.itemView.tv_cart_quantity.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.colorSecondaryText
//                    )
//                )
//            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}