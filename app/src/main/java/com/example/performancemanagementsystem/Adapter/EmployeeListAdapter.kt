package com.example.performancemanagementsystem.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.performancemanagementsystem.activities.ManagerStatusActivity
import com.example.performancemanagementsystem.R

class EmployeeListAdapter(private var context: Context, private var list : ArrayList<String>,private var IDlist : ArrayList<String>): RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val txt : TextView = view.findViewById(R.id.employeename)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employeelistlayout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt.text = list[position]


        holder.itemView.setOnClickListener {

            val intent = Intent(context, ManagerStatusActivity::class.java)

            intent.putExtra("Name", list[position])
            intent.putExtra("ID", IDlist[position])
            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}