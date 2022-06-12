/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 1:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 1:39 PM
 ************************************************/

package com.prateek.nearwe.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.birjuvachhani.locus.Locus
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.prateek.nearwe.ui.home.HomeActivity
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.User.UserModel

import com.prateek.nearwe.databinding.ActivityLoginBinding

import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    var latitude: String = ""
    var longitude: String = ""
    private lateinit var firebaseAuth: FirebaseAuth

    private val loginViewModel: LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //   initObserver()
        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignIn.setOnClickListener(View.OnClickListener {


            signInGoogle()
        })
        Locus.getCurrentLocation(this) { result ->
            result.location?.let {

                latitude=it.latitude.toString()
                longitude=it.longitude.toString()

                initObserver()
            }
            result.error?.let {

                Locus.getCurrentLocation(this) { result ->
                    result.location?.let {
                        Toast.makeText(
                            applicationContext,
                            "Location Recieved "+it.latitude+" "+it.longitude,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    result.error?.let { /* Received error! */ }
                }
            }
        }

    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var user = UserModel()
                user.Name = account.displayName
                user.EmailAddress = account.email
                user.Latitude = latitude
                user.Longitude = longitude
                loginViewModel.loginUser(user)


            }
        }
    }


    private fun initObserver() {
        loginViewModel.loginStatus.observe(this) {

            if (it) {
                startActivity(
                    Intent(
                        this, HomeActivity
                        ::class.java
                    )
                )
                finish()
            } else {
                binding.lvLogin.visibility = View.VISIBLE

            }


        }
        loginViewModel.errorMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.checkLoginStatus()
        loginViewModel.loginResponse.observe(this) {
            binding.progressBar.visibility = View.GONE

            it.Result?.let { it1 ->
                it1.User.Latitude=latitude
                it1.User.Longitude=longitude
                loginViewModel.saveUser(it1.User)
                startActivity(
                    Intent(
                        this, HomeActivity
                        ::class.java
                    )
                )
                finish()


            }


        }

    }
}