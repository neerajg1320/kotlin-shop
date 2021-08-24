package com.example.imageassetdemo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.imageassetdemo.R
import com.example.imageassetdemo.firestore.FirestoreClass
import com.example.imageassetdemo.models.Product
import com.example.imageassetdemo.ui.activities.SettingsActivity
import com.example.imageassetdemo.viewmodels.DashboardViewModel


class DashboardFragment : BaseFragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Top options menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {

                // TODO Step 9: Launch the SettingActivity on click of action item.
                // START
                startActivity(Intent(activity, SettingsActivity::class.java))
                // END
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

//        if (dashboardItemsList.size > 0) {
//
//            rv_dashboard_items.visibility = View.VISIBLE
//            tv_no_dashboard_items_found.visibility = View.GONE
//
//            rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
//            rv_dashboard_items.setHasFixedSize(true)
//
//            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
//            rv_dashboard_items.adapter = adapter
//        } else {
//            rv_dashboard_items.visibility = View.GONE
//            tv_no_dashboard_items_found.visibility = View.VISIBLE
//        }
    }
}