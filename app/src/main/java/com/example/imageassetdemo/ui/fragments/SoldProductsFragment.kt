package com.example.imageassetdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.SoldProduct
import com.example.imageassetdemo.ui.adapters.SoldProductsListAdapter

/**
 * Sold Products Listing Fragment.
 */
class SoldProductsFragment : BaseFragment() {
    private lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mRootView =  inflater.inflate(R.layout.fragment_sold_products, container, false)
        return mRootView
    }

    override fun onResume() {
        super.onResume()

        getSoldProductsList()
    }

    private fun getSoldProductsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class.
        FirestoreClass().getSoldProductsList(this@SoldProductsFragment)
    }

    /**
     * A function to get the list of sold products.
     */
    fun successSoldProductsList(soldProductsList: ArrayList<SoldProduct>) {

        // Hide Progress dialog.
        hideProgressDialog()

        val rvSoldProductItems = mRootView.findViewById<RecyclerView>(R.id.rv_sold_product_items)
        val tvNoSoldProductsFound = mRootView.findViewById<TextView>(R.id.tv_no_sold_products_found)
        if (soldProductsList.size > 0) {
            rvSoldProductItems.visibility = View.VISIBLE
            tvNoSoldProductsFound.visibility = View.GONE

            rvSoldProductItems.layoutManager = LinearLayoutManager(activity)
            rvSoldProductItems.setHasFixedSize(true)

            val soldProductsListAdapter =
                SoldProductsListAdapter(requireActivity(), soldProductsList)
            rvSoldProductItems.adapter = soldProductsListAdapter
        } else {
            rvSoldProductItems.visibility = View.GONE
            tvNoSoldProductsFound.visibility = View.VISIBLE
        }
    }
}