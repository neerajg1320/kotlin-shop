package com.example.imageassetdemo.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.databinding.ItemListLayoutBinding
import com.example.imageassetdemo.models.Order
import com.example.imageassetdemo.ui.activities.MyOrderDetailsActivity
import com.example.imageassetdemo.util.Constants
import com.example.imageassetdemo.util.GlideLoader


open class MyOrdersListAdapter(
    private val context: Context,
    private var orderList: ArrayList<Order>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class OrderViewHolder(val binding: ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemListLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
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
        val model = orderList[position]

        with(holder as OrderViewHolder) {
            with(orderList[position]) {
                GlideLoader(context).loadProductPicture(
                    model.image,
                    binding.ivItemImage
                )

                binding.tvItemName.text = this.title
                binding.tvItemPrice.text = "$${this.total_amount}"

                binding.ibDeleteProduct.visibility = View.GONE

                holder.itemView.setOnClickListener {
                    val intent = Intent(context, MyOrderDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_MY_ORDER_DETAILS, this)
                    context.startActivity(intent)
                }
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return orderList.size
    }

}
// END