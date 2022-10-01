package com.example.performancemanagementsystem.utils

import androidx.fragment.app.Fragment
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.fragments.*

class Constants {
    companion object {
        const val USERNAME = "USERNAME"
        const val EMAIL = "EMAIL"
        const val USER_UID = "USER_UID"
        const val AUTH_CONTAINER = R.id.auth_container
        const val DASH_CONTAINER = R.id.dash_container
        const val NEW_ORG = "NEW_ORG"
        const val NEW_MEMBER = "NEW_MEMBER"
        const val CODE = "CODE"
        const val REGISTER_FRAG = "REGISTER_FRAG"
        const val NEW_FEED = "NEW_FEED"
        const val DASH_FRAG = "DASH_FRAG"
        const val PENDING_FEEDBACK = "PENDING_FEEDBACK"
         fun getFragmentClass(id: String): Class<Fragment> {
             return when(id) {
                 REGISTER_FRAG -> RegisterFragment::class.java as Class<Fragment>
                 NEW_ORG -> NewOrgFragment::class.java as Class<Fragment>
                 NEW_MEMBER -> NewMemberFragment::class.java as Class<Fragment>
                 PENDING_FEEDBACK -> PendingFeedback::class.java as Class<Fragment>
                 NEW_FEED -> newFeedFragment::class.java as Class<Fragment>
                 DASH_FRAG -> DashFragment::class.java as Class<Fragment>
                 else -> NewOrgFragment::class.java as Class<Fragment>
             }
         }
    }
}