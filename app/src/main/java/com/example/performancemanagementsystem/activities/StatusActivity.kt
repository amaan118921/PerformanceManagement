package com.example.performancemanagementsystem.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.performancemanagementsystem.fragments.StatusFragment
import com.example.performancemanagementsystem.R
import com.example.performancemanagementsystem.dataModel.AnswersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatusActivity : AppCompatActivity() {

    private lateinit var dbrefGraphData : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        dbrefGraphData = FirebaseDatabase.getInstance().getReference("GraphData")

        val extras : Bundle = intent.extras!!
        val username : String = extras.get("Name").toString()

        val dbrefAnswer = FirebaseDatabase.getInstance().getReference("Answers")
        Log.i("Uid Current",FirebaseAuth.getInstance().currentUser!!.uid)
        dbrefAnswer.addListenerForSingleValueEvent(object : ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(snap in snapshot.children)
                    {
                       val model = snap.getValue(AnswersModel::class.java)
                        Log.i("Uid Current",model!!.memberId)
                        if(model!!.memberId == FirebaseAuth.getInstance().currentUser!!.uid)
                        {
                            Log.i("Uid Current",FirebaseAuth.getInstance().currentUser!!.uid)
                            val avglist : Array<Int> = Array(100){0}
                            val noOfMembers = model.reponses.size
                            for(i in model.reponses)
                            {
                                val answerforeach :List<String> = i.value

                                for(j in answerforeach.indices){
                                    var value = 0
                                    when(answerforeach[j])
                                    {
                                        "Excellent"->{
                                            value = 4
                                        }
                                        "Good"->{
                                            value = 3
                                        }
                                        "Bad"->{
                                            value = 2
                                        }
                                        "Worst"->{
                                            value = 1
                                        }

                                    }
                                    avglist[j] = avglist[j]+value
                                }
                            }

                            val arrayList : ArrayList<Double> =ArrayList()
                            for(x in 0..99)
                            {
                                if(avglist[x]!=0){
                                    val d = (avglist[x].toDouble())/noOfMembers
                                arrayList.add(d)
                                }
                            }
                            Log.i("Avg lsit valies",arrayList.toString())
                            Toast.makeText(this@StatusActivity,arrayList.toString(),Toast.LENGTH_SHORT).show()







                            dbrefGraphData.addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if(snapshot.exists()){
                                        dbrefGraphData.child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(arrayList)
                                    }
                                    else{
                                        val map : HashMap<String,ArrayList<Double>> = hashMapOf()
                                        map.put(FirebaseAuth.getInstance().currentUser!!.uid,arrayList)
                                        dbrefGraphData.setValue(map)

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }


                            })




                            break
                        }
                    }

                }
                else{
                    Log.i("Snapshot","Doesnot exist")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Database","Doesnot exist")
            }


        })


        supportFragmentManager.beginTransaction()
            .add(R.id.statusContainer, StatusFragment(username))
            .commit()


    }
}