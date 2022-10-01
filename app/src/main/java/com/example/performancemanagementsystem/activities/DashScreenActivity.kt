package com.example.performancemanagementsystem.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.performancemanagementsystem.fragments.DashFragment
import com.example.performancemanagementsystem.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_screen)


        if(FirebaseAuth.getInstance().currentUser == null){
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }





//        val extras : Bundle = intent.extras!!
//        val companyCode : String = extras.getInt("companyCode").toString()
        else{
        supportFragmentManager.beginTransaction()
            .add(R.id.dash_container, DashFragment())
            .commit()}


    }
}