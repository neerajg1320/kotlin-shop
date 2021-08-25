package com.example.imageassetdemo.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageassetdemo.R
import com.example.imageassetdemo.databinding.ActivityCartListBinding
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.CartItem
import com.example.imageassetdemo.models.Product
import com.example.imageassetdemo.ui.adapters.CartItemsListViewBindingAdapter

class CartListActivity : BaseActivity() {
    private lateinit var binding: ActivityCartListBinding

    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar(binding.toolbarCartListActivity)
    }

    override fun onResume() {
        super.onResume()

        getProductList()
    }

    /**
     * A function to get product list to compare the current stock with the cart items.
     */
    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAllProductsList(this@CartListActivity)
    }

    /**
     * A function to get the success result of product list.
     *
     * @param productsList
     */
    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        mProductsList = productsList
        getCartItemsList()
    }


    /**
     * A function to get the list of cart items in the activity.
     */
    private fun getCartItemsList() {
        FirestoreClass().getCartList(this@CartListActivity)
    }

    /**
     * A function to notify the success result of the cart items list from cloud firestore.
     *
     * @param cartList
     */
    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        // Hide progress dialog.
        hideProgressDialog()

//        Log.i("CartListActivity", "successCartItemsList")
//        for (i in cartList) {
//            Log.i("Cart Item Title", i.title)
//        }
        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {

                    cart.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0) {
                        cart.cart_quantity = product.stock_quantity
                    }
                }
            }
        }

        mCartListItems = cartList

        val rvCardItemsList = binding.rvCartItemsList

        if (mCartListItems.size > 0) {
            rvCardItemsList.visibility = View.VISIBLE
            binding.llCheckout.visibility = View.VISIBLE
            binding.tvNoCartItemFound.visibility = View.GONE

            rvCardItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
            rvCardItemsList.setHasFixedSize(true)

            // val cartListAdapter = CartItemsListAdapter(this@CartListActivity, mCartListItems)
            val cartListAdapter = CartItemsListViewBindingAdapter(this@CartListActivity, mCartListItems)
            rvCardItemsList.adapter = cartListAdapter

            var subTotal: Double = 0.0

            for (item in mCartListItems) {
                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }
            }

            binding.tvSubTotal.text = "$$subTotal"
            // Here we have kept Shipping Charge is fixed as $10 but in your case it may cary. Also, it depends on the location and total amount.
            binding.tvShippingCharge.text = "$10.0"

            if (subTotal > 0) {
                binding.llCheckout.visibility = View.VISIBLE

                val total = subTotal + 10
                binding.tvTotalAmount.text = "$$total"
            } else {
                binding.llCheckout.visibility = View.GONE
            }

        } else {
            rvCardItemsList.visibility = View.GONE
            binding.llCheckout.visibility = View.GONE
            binding.tvNoCartItemFound.visibility = View.VISIBLE
        }

    }


    /**
     * A function to notify the user about the item removed from the cart list.
     */
    fun itemRemovedSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@CartListActivity,
            resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getCartItemsList()
    }

    /**
     * A function to notify the user about the item quantity updated in the cart list.
     */
    fun itemUpdateSuccess() {

        hideProgressDialog()

        getCartItemsList()
    }

}