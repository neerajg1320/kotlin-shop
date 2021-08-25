package com.example.imageassetdemo.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageassetdemo.databinding.ItemAddressLayoutBinding
import com.example.imageassetdemo.databinding.ItemCartLayoutBinding
import com.example.imageassetdemo.models.Address
import com.example.imageassetdemo.ui.activities.AddEditAddressActivity
import com.example.imageassetdemo.util.Constants

/**
 * An adapter class for AddressList adapter.
 */
open class AddressListAdapter(
    private val context: Context,
    private var addressList: ArrayList<Address>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class AddressViewHolder(val binding: ItemAddressLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemAddressLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return AddressViewHolder(binding)
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
        val model = addressList[position]

        with (holder as AddressViewHolder) {
            binding.tvAddressFullName.text = model.name
            binding.tvAddressType.text = model.type
            binding.tvAddressDetails.text = "${model.address}, ${model.zipCode}"
            binding.tvAddressMobileNumber.text = model.mobileNumber
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return addressList.size
    }

    // TODO Step 4: Create a function to function to edit the address details and pass the existing details through intent.
    /**
     * A function to edit the address details and pass the existing details through intent.
     *
     * @param activity
     * @param position
     */
    fun notifyEditItem(activity: Activity, position: Int) {
        val intent = Intent(context, AddEditAddressActivity::class.java)
        intent.putExtra(Constants.EXTRA_ADDRESS_DETAILS, addressList[position])
        activity.startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)

        notifyItemChanged(position) // Notify any registered observers that the item at position has changed.
    }
}