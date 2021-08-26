package com.example.imageassetdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.Order
import com.example.imageassetdemo.ui.adapters.MyOrdersListAdapter
import com.example.imageassetdemo.util.CustomTextView

class OrdersFragment : BaseFragment() {
    private lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_orders, container, false)
        return mRootView
    }


    override fun onResume() {
        super.onResume()

        getMyOrdersList()
    }

    /**
     * A function to get the list of my orders.
     */
    private fun getMyOrdersList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getMyOrdersList(this@OrdersFragment)
    }

    /**
     * A function to get the success result of the my order list from cloud firestore.
     *
     * @param ordersList List of my orders.
     */
    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {

        // Hide the progress dialog.
        hideProgressDialog()

        val rvMyOrderItems = mRootView.findViewById<RecyclerView>(R.id.rv_my_order_items)
        val tvNoOrdersFound = mRootView.findViewById<CustomTextView>(R.id.tv_no_orders_found)
        if (ordersList.size > 0) {

            rvMyOrderItems.visibility = View.VISIBLE
            tvNoOrdersFound.visibility = View.GONE

            rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
            rvMyOrderItems.setHasFixedSize(true)

            val myOrdersAdapter = MyOrdersListAdapter(requireActivity(), ordersList)
            rvMyOrderItems.adapter = myOrdersAdapter
        } else {
            rvMyOrderItems.visibility = View.GONE
            tvNoOrdersFound.visibility = View.VISIBLE
        }
    }
}