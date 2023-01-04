package com.udacity.project4.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityAuthenticationBinding
import com.udacity.project4.locationreminders.RemindersActivity

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {

    private  var viewModel  = AuthenticationActivityViewModel()
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
        viewModel.checkUser()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // initialize the view model
        viewModel = ViewModelProvider(this).get(AuthenticationActivityViewModel::class.java)
        // check if the user is logged in
        viewModel.checkUser()
        // set the view model to the binding
        binding.viewModel = viewModel
        // navigate to the reminders activity if the user is already logged in
        viewModel.navigateToRemindersActivity.observe(this) {
            if (it) {
                startActivity(Intent(this, RemindersActivity::class.java))
                finish()
            }
        }
        with(binding){
            login.setOnClickListener {
                createAnAccount()
            }
        }
    }
    private fun createAnAccount() {
        // provide options for sign in
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        // customise layout
        val customLayout =
            AuthMethodPickerLayout
                .Builder(R.layout.custome_authentication)
                .setGoogleButtonId(R.id.btn_google)
                .setEmailButtonId(R.id.btn_email)
                .build()

        // launch sign in
        val singIn = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setAuthMethodPickerLayout(customLayout)
            .build()
        signInLauncher.launch(singIn)
    }
}
