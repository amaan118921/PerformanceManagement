package com.example.performancemanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.performancemanagementsystem.dataModel.CompanyInfoModel
import com.example.performancemanagementsystem.activities.DashScreenActivity
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.dataModel.UserModel
import com.example.performancemanagementsystem.databinding.FragmentNewOrgBinding
import com.example.performancemanagementsystem.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
@AndroidEntryPoint
class NewOrgFragment : BaseFragment() {

    private lateinit var newOrgBinding : FragmentNewOrgBinding
    @Inject
    lateinit var database : FirebaseDatabase
    @Inject
    lateinit var auth : FirebaseAuth
    private lateinit var dbref : DatabaseReference
    private var username: String? = null
    private var email: String? = null
    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.getString(Constants.USERNAME)
        email = arguments?.getString(Constants.EMAIL)
        uid = arguments?.getString(Constants.USER_UID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("CompanyInfo")
        auth = FirebaseAuth.getInstance()


        newOrgBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_org,
            container,
            false
        )


        var companyName :String?=null
        var hrName : String?=null

        val num = generatePin()

        generatedcompanyCode = num

        newOrgBinding.genePin.text= num.toString()


        newOrgBinding.done.setOnClickListener {




            companyName = newOrgBinding.companyName.text.toString()
            hrName = newOrgBinding.hRName.text.toString()
            if(companyName!!.isEmpty())
            {
                newOrgBinding.companyName.error = "Field Required"
                return@setOnClickListener
            }
            if(hrName!!.isEmpty())
            {
                newOrgBinding.hRName.error = "Field Required"
                return@setOnClickListener
            }



            val user = username?.let { it1 -> uid?.let { it2 -> email?.let { it3 ->
                UserModel(it1,
                    it3, it2)
            } } }
            val members = ArrayList<UserModel>()
            user?.let { it1 -> members.add(it1) }

            val companyInfoModel = CompanyInfoModel(
                auth.currentUser!!.uid,
                companyName!!,
                hrName!!,
                num.toString(),
                members


            )

            dbref.child(auth.uid!!).setValue(companyInfoModel)

//            val feedlist = ArrayList<String>()
//            feedlist!!.add("")
//            var dbrefFeedbackList = FirebaseDatabase.getInstance().getReference("FeedbackList")
//            dbrefFeedbackList.child(num.toString()).child(auth.uid!!).setValue(feedlist)

            val intent = Intent(context, DashScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("companyCode",num)
            startActivity(intent)
            activity?.finish()


        }

        newOrgBinding.shareBtn.setOnClickListener {

            //val message = num.toString()

             companyName = newOrgBinding.companyName.text.toString()
             hrName = newOrgBinding.hRName.text.toString()

            if(companyName!!.isEmpty())
            {
                newOrgBinding.companyName.error = "Field Required"
                return@setOnClickListener
            }
            if(hrName!!.isEmpty())
            {
                newOrgBinding.hRName.error = "Field Required"
                return@setOnClickListener
            }

            val message = "Organisation Name : $companyName \nUnder HR : $hrName \nCode : $num"

            val intent = Intent()
            intent.action= Intent.ACTION_SEND

            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share Via :"))
        }
        return newOrgBinding.root
    }

    @Throws(Exception::class)
    fun generatePin(): Int {
        val generator = Random()
        generator.setSeed(System.currentTimeMillis())
        var num: Int = generator.nextInt(99999) + 99999
        if (num < 100000 || num > 999999) {
            num = generator.nextInt(99999) + 99999
            if (num < 100000 || num > 999999) {
                throw Exception("Unable to generate PIN at this time..")
            }
        }
        return num
    }

    companion object{
        var generatedcompanyCode : Int = 0
    }
}