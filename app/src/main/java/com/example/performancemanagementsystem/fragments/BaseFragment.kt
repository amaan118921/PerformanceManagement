package com.example.performancemanagementsystem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.activities.MainActivity
import com.example.performancemanagementsystem.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {

  fun addFragment(id: String, containerID: Int, args: Bundle?) {
      getFragSupportManager().beginTransaction().setReorderingAllowed(true).addToBackStack(id).add(containerID,
      Constants.getFragmentClass(id), args)
  }

    fun replaceFragment(id: String, containerID: Int, args: Bundle?) {
        getFragSupportManager().beginTransaction().setReorderingAllowed(true).replace(containerID,
            Constants.getFragmentClass(id), args)
    }


    private fun getFragSupportManager(): FragmentManager = requireActivity().supportFragmentManager

}