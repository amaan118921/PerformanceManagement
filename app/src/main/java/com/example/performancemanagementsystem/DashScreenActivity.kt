package com.example.performancemanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.performancemanagementsystem.Fragments.DashFragment
import com.example.performancemanagementsystem.Fragments.newFeedFragment
import com.google.firebase.auth.FirebaseAuth

class DashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_screen)


        val auth = FirebaseAuth.getInstance()
        Toast.makeText(this,auth.currentUser?.uid,Toast.LENGTH_SHORT).show()


        val extras : Bundle = intent.extras!!
        val companyCode : String = extras.getInt("companyCode").toString()
        supportFragmentManager.beginTransaction()
            .add(R.id.dash_container, DashFragment(companyCode))
            .commit()


    }
}