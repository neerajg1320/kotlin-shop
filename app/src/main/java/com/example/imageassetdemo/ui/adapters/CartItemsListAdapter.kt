package com.example.imageassetdemo.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.databinding.ItemCartLayoutBinding
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.CartItem
import com.example.imageassetdemo.ui.activities.CartListActivity
import com.example.imageassetdemo.util.Constants
import com.example.imageassetdemo.util.GlideLoader

/**
 * A adapter class for dashboard items list.
 */
open class CartItemsListAdapter(
    private val context: Context,
    private var cartItemsList: ArrayList<CartItem>,
    private val updateCartItems: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    inner class CartItemViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCartLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CartItemViewHolder(binding)
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
        with(holder as CartItemViewHolder) {
            with(cartItemsList[position]) {
                GlideLoader(context).loadProductPicture(this.image, binding.ivCartItemImage)
                binding.tvCartItemTitle.text = this.title
                binding.tvCartItemPrice.text = "$${this.price}"
                binding.tvCartQuantity.text = this.cart_quantity

                if (this.cart_quantity == "0") {
                    binding.ibRemoveCartItem.visibility = View.GONE
                    binding.ibAddCartItem.visibility = View.GONE

                    if (updateCartItems) {
                        binding.ibDeleteCartItem.visibility = View.VISIBLE
                    } else {
                        binding.ibDeleteCartItem.visibility = View.GONE
                    }

                    binding.tvCartQuantity.text =
                        context.resources.getString(R.string.lbl_out_of_stock)

                    binding.tvCartQuantity.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorSnackBarError
                        )
                    )
                } else {
                    if (updateCartItems) {
                        binding.ibRemoveCartItem.visibility = View.VISIBLE
                        binding.ibAddCartItem.visibility = View.VISIBLE
                        binding.ibDeleteCartItem.visibility = View.VISIBLE
                    } else {
                        binding.ibRemoveCartItem.visibility = View.GONE
                        binding.ibAddCartItem.visibility = View.GONE
                        binding.ibDeleteCartItem.visibility = View.GONE

                    }

                    binding.tvCartQuantity.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorSecondaryText
                        )
                    )
                }

                // Add item quantity to order by one unit
                binding.ibAddCartItem.setOnClickListener {
                    val cartQuantity: Int = this.cart_quantity.toInt()

                    if (cartQuantity < this.stock_quantity.toInt()) {
                        val itemHashMap = HashMap<String, Any>()
                        itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()

                        // Show the progress dialog.
                        if (context is CartListActivity) {
                            context.showProgressDialog(context.resources.getString(R.string.please_wait))
                        }

                        FirestoreClass().updateMyCart(context, this.id, itemHashMap)
                    } else {
                        if (context is CartListActivity) {
                            context.showErrorSnackBar(
                                context.resources.getString(
                                    R.string.msg_for_available_stock,
                                    this.stock_quantity
                                ),
                                true
                            )
                        }
                    }
                }

                // Remove item from order by one unit
                binding.ibRemoveCartItem.setOnClickListener {
                    if (this.cart_quantity == "1") {
                        FirestoreClass().deleteItemFromCart(context, this.id)
                    } else {
                        val cartQuantity: Int = this.cart_quantity.toInt()
                        val itemHashMap = HashMap<String, Any>()
                        itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()

                        // Show the progress dialog.
                        if (context is CartListActivity) {
                            context.showProgressDialog(context.resources.getString(R.string.please_wait))
                        }

                        FirestoreClass().updateMyCart(context, this.id, itemHashMap)
                    }
                }

                binding.ibDeleteCartItem.setOnClickListener {
                    when (context) {
                        is CartListActivity -> {
                            context.showProgressDialog(context.resources.getString(R.string.please_wait))
                        }
                    }

                    FirestoreClass().deleteItemFromCart(context, this.id)
                }
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return cartItemsList.size
    }
}