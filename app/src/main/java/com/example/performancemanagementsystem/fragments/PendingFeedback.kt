package com.example.performancemanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.performancemanagementsystem.Adapter.FeedbackListAdapter
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingFeedback : BaseFragment() {

   private var cmpcode: String? = null
   private lateinit var dbrefFeedbackList : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cmpcode = arguments?.getString(Constants.CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_pending_feedback, container, false)
        dbrefFeedbackList = FirebaseDatabase.getInstance().getReference("FeedbackList")

        val btn : ImageView = view.findViewById(R.id.backbtn)
        val recyclerView : RecyclerView = view.findViewById(R.id.feedbackList)


        btn.setOnClickListener {
            replaceFragment(Constants.DASH_FRAG, Constants.DASH_CONTAINER, null)
        }



        cmpcode?.let {
            dbrefFeedbackList.child(it).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {

                        if(snap.key == FirebaseAuth.getInstance().uid){

                            val feedarray: ArrayList<String> = ArrayList()
                            for(i in snap.children) {
                                feedarray.add(i.value.toString())
                            }

                            recyclerView.apply {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = FeedbackListAdapter(activity , feedarray)
                            }
                        }
                    }

                }


                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }


        return view
    }





}