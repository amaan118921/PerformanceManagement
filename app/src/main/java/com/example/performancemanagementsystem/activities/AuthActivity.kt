package com.example.performancemanagementsystem.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.performancemanagementsystem.fragments.LoginFragment
import com.example.performancemanagementsystem.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction()
                .add(R.id.auth_container, LoginFragment())
                .commit()
    }
}