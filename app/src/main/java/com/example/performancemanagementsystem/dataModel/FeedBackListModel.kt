package com.example.performancemanagementsystem.dataModel

data class FeedBackListModel(
    val feedbackList : ArrayList<String>?
){
    constructor():this(null)
}
