package com.example.imageassetdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.example.imageassetdemo.R
import com.example.imageassetdemo.util.BoldTextView
import com.example.imageassetdemo.util.CustomButton
import com.example.imageassetdemo.util.CustomEditText

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<BoldTextView>(R.id.tv_login).setOnClickListener {

            // Launch the register screen when the user clicks on the text.
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupActionBar(findViewById<Toolbar>(R.id.toolbar_register_activity))

        findViewById<CustomButton>(R.id.btn_register).setOnClickListener {
            validateRegisterDetails()
        }
    }

    private fun setupActionBar(toolbar_register_activity: Toolbar) {
        setSupportActionBar(toolbar_register_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(findViewById<CustomEditText>(R.id.et_first_name).text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

//            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
//                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
//                false
//            }
//
//            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
//                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
//                false
//            }
//
//            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
//                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
//                false
//            }
//
//            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
//                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
//                false
//            }
//
//            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
//                .trim { it <= ' ' } -> {
//                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
//                false
//            }
//            !cb_terms_and_condition.isChecked -> {
//                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
//                false
//            }
            else -> {
                showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }
}