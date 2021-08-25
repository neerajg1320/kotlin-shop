package com.example.imageassetdemo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imageassetdemo.R
import com.example.imageassetdemo.databinding.ActivityAddressListBinding

class AddressListActivity : BaseActivity() {
    private lateinit var binding: ActivityAddressListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar(binding.toolbarAddressListActivity)
    }
}