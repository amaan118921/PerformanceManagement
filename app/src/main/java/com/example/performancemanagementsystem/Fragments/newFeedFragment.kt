package com.example.performancemanagementsystem.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.performancemanagementsystem.Adapter.FeedbackListAdapter
import com.example.performancemanagementsystem.Adapter.QuestionListAdapter
import com.example.performancemanagementsystem.CompanyInfoModel
import com.example.performancemanagementsystem.FeedBackListModel
import com.example.performancemanagementsystem.FeedbackModel
import com.example.performancemanagementsystem.Fragments.NewOrgFragment.Companion.generatedcompanyCode
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.databinding.FragmentNewFeedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class newFeedFragment() : Fragment() {

    private lateinit var newFeedBinding: FragmentNewFeedBinding

   private lateinit var code : String




    private  var arrayList: ArrayList<String> = ArrayList()



    private var feedb = hashMapOf<String,ArrayList<String>>()

    private lateinit var member : String
    private lateinit var dbref : DatabaseReference
    private lateinit var dbrefCompanyInfo : DatabaseReference
    private lateinit var dbrefFeedbackList :DatabaseReference

    private var keyValue = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dbrefCompanyInfo = FirebaseDatabase.getInstance().getReference("CompanyInfo")

        dbrefFeedbackList = FirebaseDatabase.getInstance().getReference("FeedbackList")









        newFeedBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_feed,
            container,
            false
        )

        dbref = FirebaseDatabase.getInstance().getReference("Feedback")
        code = "0"


//        if(generatedcompanyCode==0)
//        {
            dbrefCompanyInfo.child(FirebaseAuth.getInstance().uid!!).child("companyCode").get()
                .addOnSuccessListener {
                    code = it.value.toString()
                    Log.i("firebase", "Got value ${it.value}")
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
//        }
//        else
//            code = generatedcompanyCode.toString()



       // arrayList = ArrayList()

        newFeedBinding.floating.setOnClickListener {

            addQues()
        }

        newFeedBinding.submit.setOnClickListener {

            funcsubmit()

        }


        return newFeedBinding.root
    }

    private fun addQues() {

        val ques : String = newFeedBinding.question.text.toString()
        if( ques.isEmpty())
        {
            newFeedBinding.question.error = "Field Required"
            return
        }



        if(arrayList.isEmpty())
        {
            val key = dbref.push().key
            keyValue = key.toString()



            arrayList.add(ques)
            if (key != null) {


                val feedback = FeedbackModel(key!!, code, "", arrayList)
                dbref.child(key).setValue(feedback)
            }

        }
        else
        {
            arrayList.add(ques)
            val feedback = FeedbackModel(keyValue, code, "", arrayList)
            dbref.child(keyValue).setValue(feedback)

        }

        val empty:String  = ""
        newFeedBinding.question.setText(empty)


        dbref.child(keyValue).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val feedback = snapshot.getValue(FeedbackModel::class.java)
                val quelist = feedback!!.questionList
                newFeedBinding.questionList.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = QuestionListAdapter(quelist!!)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





    }

    private fun funcsubmit() {


        var arrayList = ArrayList<String>()

//        dbfirestore.collection(code).document(FirebaseAuth.getInstance().uid!!).get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                arrayList = document["Feedback Key",ArrayList<String>]
//                feedb = document.data as HashMap<String,ArrayList<String>>
//
//            } else {
//                feedb = hashMapOf()
//                Log.d(TAG, "No such document")
//            }
//        }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//
//
//        if(feedb.isEmpty()){
//            feedb.put("Feedback Key", keyValue)
//
//        }
//        else{
//            feedb.put("Feedback Key", keyValue)
//
//        }
//        feedb.put("Feedback Key",keyValue )
//        Log.i("feedb",feedb.toString())




//        dbfirestore.collection(code).document(FirebaseAuth.getInstance().uid!!).set(feedb).addOnSuccessListener {
//            Log.i("Document", "Completed")
//        }
//            .addOnFailureListener {
//                Log.i("Document", "Failure")
//            }
//



        dbrefFeedbackList.child(code).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                val feedList : ArrayList<String> = snapshot.getValue() as ArrayList<String>
                    if(feedList[0] == ""){
                        feedList[0]=keyValue
                    }
                    else
                        feedList.add(keyValue)
                    Log.i("Key",snapshot.key!!)
                dbrefFeedbackList.child(code).child(snapshot.key!!).setValue(feedList)}
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })






        member = newFeedBinding.memberName.text.toString()

        if( member.isEmpty())
        {
            newFeedBinding.memberName.error = "Field Required"
            return
        }
        dbref.child(keyValue).child("member").setValue(member)





        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.dash_container, DashFragment())
            .commit()





    }






}