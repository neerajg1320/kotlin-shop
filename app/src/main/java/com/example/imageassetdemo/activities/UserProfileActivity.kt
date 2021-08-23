package com.example.imageassetdemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imageassetdemo.R
import com.example.imageassetdemo.databinding.ActivityUserProfileBinding
import com.example.imageassetdemo.models.User
import com.example.imageassetdemo.util.Constants

class UserProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userDetails: User = User()
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra<User>(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userDetails.firstName)
        binding.etLastName.isEnabled = false
        binding.etLastName.setText(userDetails.lastName)
        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userDetails.email)

    }
}