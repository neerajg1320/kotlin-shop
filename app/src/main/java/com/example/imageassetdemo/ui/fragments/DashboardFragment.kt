package com.example.imageassetdemo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.R
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.Product
import com.example.imageassetdemo.ui.activities.ProductDetailsActivity
import com.example.imageassetdemo.ui.activities.SettingsActivity
import com.example.imageassetdemo.ui.adapters.DashboardItemsListAdapter
import com.example.imageassetdemo.util.Constants
import com.example.imageassetdemo.viewmodels.DashboardViewModel


class DashboardFragment : BaseFragment() {

    private lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return mRootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {

                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getDashboardItemsList()
    }

    /**
     * A function to get the dashboard items list from cloud firestore.
     */
    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)
    }

    /**
     * A function to get the success result of the dashboard items from cloud firestore.
     *
     * @param dashboardItemsList
     */
    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {

        // Hide the progress dialog.
        hideProgressDialog()

        val rvDashboard: RecyclerView = mRootView.findViewById(R.id.rv_dashboard_items)
        val tvNoDashboardItems: TextView = mRootView.findViewById(R.id.tv_no_dashboard_items_found)
        if (dashboardItemsList.size > 0) {

            rvDashboard.visibility = View.VISIBLE
            tvNoDashboardItems.visibility = View.GONE

            rvDashboard.layoutManager = GridLayoutManager(activity, 2)
            rvDashboard.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            rvDashboard.adapter = adapter

            adapter.setOnClickListener(object:DashboardItemsListAdapter.OnClickListener{
                override fun onClick(position: Int, product: Product) {
                    // Launch Product details screen.
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
                    startActivity(intent)
                }
            })
        } else {
            rvDashboard.visibility = View.GONE
            tvNoDashboardItems.visibility = View.VISIBLE
        }
    }
}