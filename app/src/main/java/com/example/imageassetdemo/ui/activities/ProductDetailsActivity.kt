package com.example.imageassetdemo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.imageassetdemo.R
import com.example.imageassetdemo.databinding.ActivityProductDetailsBinding
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.CartItem
import com.example.imageassetdemo.models.Product
import com.example.imageassetdemo.util.Constants
import com.example.imageassetdemo.util.GlideLoader

class ProductDetailsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProductDetailsBinding

    private var mProductId: String = ""
    private lateinit var mProductDetails: Product


    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding.root)

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
        }
        var productOwnerId: String = ""
        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            productOwnerId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

        Log.d("OwnerId", "${FirestoreClass().getCurrentUserID()} , ${productOwnerId}")
        if (FirestoreClass().getCurrentUserID() == productOwnerId) {
            binding.btnAddToCart.visibility = View.GONE
            binding.btnGoToCart.visibility = View.GONE
        } else {
            binding.btnAddToCart.visibility = View.VISIBLE
        }

        setupActionBar(binding.toolbarProductDetailsActivity)

        getProductDetails()

        binding.btnAddToCart.setOnClickListener(this)
        binding.btnGoToCart.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.btn_add_to_cart -> {
                    addToCart()
                }

                R.id.btn_go_to_cart->{
                    startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
                }
            }
        }
    }

    /**
     * A function to prepare the cart item to add it to the cart in cloud firestore.
     */
    private fun addToCart() {

        val addToCart = CartItem(
            FirestoreClass().getCurrentUserID(),
            mProductId,
            mProductDetails.title,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )

        // Show the progress dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().addCartItems(this@ProductDetailsActivity, addToCart)
    }

    /**
     * A function to notify the success result of item added to the to cart.
     */
    fun addToCartSuccess() {
        // Hide the progress dialog.
        hideProgressDialog()

        Toast.makeText(
            this@ProductDetailsActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        ).show()

        // Hide the AddToCart button if the item is already in the cart.
        binding.btnAddToCart.visibility = View.GONE
        // Show the GoToCart button if the item is already in the cart. User can update the quantity from the cart list screen if he wants.
        binding.btnGoToCart.visibility = View.VISIBLE
    }

    /**
     * A function to call the firestore class function that will get the product details from cloud firestore based on the product id.
     */
    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FirestoreClass to get the product details.
        FirestoreClass().getProductDetails(this@ProductDetailsActivity, mProductId)
    }

    /**
     * A function to notify the success result of the product details based on the product id.
     *
     * @param product A model class with product details.
     */
    fun productDetailsSuccess(product: Product) {
        mProductDetails = product

        // Hide Progress dialog.
        hideProgressDialog()

        // Populate the product details in the UI.
        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsStockQuantity.text = product.stock_quantity

        if(product.stock_quantity.toInt() == 0){
            hideProgressDialog()

            binding.btnAddToCart.visibility = View.GONE
            binding.tvProductDetailsStockQuantity.text =
                resources.getString(R.string.lbl_out_of_stock)
            binding.tvProductDetailsStockQuantity.setTextColor(
                ContextCompat.getColor(
                    this@ProductDetailsActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            // There is no need to check the cart list if the product owner himself is seeing the product details.
            if (FirestoreClass().getCurrentUserID() == product.user_id) {
                hideProgressDialog()
            } else {
                FirestoreClass().checkIfItemExistInCart(this@ProductDetailsActivity, mProductId)
            }
        }
    }

    /**
     * A function to notify the success result of item exists in the cart.
     */
    fun productExistsInCart() {
        // Hide the progress dialog.
        hideProgressDialog()

        // Hide the AddToCart button if the item is already in the cart.
        binding.btnAddToCart.visibility = View.GONE
        // Show the GoToCart button if the item is already in the cart. User can update the quantity from the cart list screen if he wants.
        binding.btnGoToCart.visibility = View.VISIBLE
    }

}