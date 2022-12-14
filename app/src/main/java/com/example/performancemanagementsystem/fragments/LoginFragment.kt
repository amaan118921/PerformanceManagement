package com.example.performancemanagementsystem.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.performancemanagementsystem.activities.DashScreenActivity
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.databinding.FragmentLoginBinding
import com.example.performancemanagementsystem.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var loginBinding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)


        loginBinding.loginButton.setOnClickListener {

            funLogin()
        }
        loginBinding.btnSignup.setOnClickListener {
            replaceFragment(Constants.REGISTER_FRAG, Constants.AUTH_CONTAINER, null)
        }






        return loginBinding.root

    }

    private fun funLogin() {

        val email: String = loginBinding.etText.text.toString()
        val password: String = loginBinding.etPass.text.toString()



        if (email.isEmpty()) {

            loginBinding.etText.error = "Field is Required"
            return
        }
        if (password.isEmpty()) {

            loginBinding.etPass.error = "Field is Required"
            return
        }



        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser


                    val intent = Intent(context, DashScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    activity?.finish()


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }
            }


    }


}