package com.example.performancemanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.databinding.FragmentNewMemberBinding
import com.example.performancemanagementsystem.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMemberFragment : BaseFragment() {

    private var username: String? = null
    private var email: String? = null
    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.getString(Constants.USERNAME)
        email = arguments?.getString(Constants.EMAIL)
        uid = arguments?.getString(Constants.USER_UID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val newMemberBinding : FragmentNewMemberBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_member,container,false)

        newMemberBinding.newOrg.setOnClickListener {
            Constants.apply {
                Bundle().apply {
                    putString(USERNAME, username)
                    putString(EMAIL, email)
                    putString(USER_UID, uid)
                    replaceFragment(NEW_ORG, AUTH_CONTAINER, this)
                }
            }
        }


        newMemberBinding.existOrg.setOnClickListener {
            username?.let { it1 -> email?.let { it2 -> uid?.let { it3 -> CustomDialog(it1, it2, it3).show(requireFragmentManager(),"Custom Dialog") } } }
        }
        return newMemberBinding.root
    }


}