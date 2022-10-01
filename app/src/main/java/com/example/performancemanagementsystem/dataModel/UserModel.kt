package com.example.performancemanagementsystem.dataModel

data class UserModel(
    var name : String,
    val email : String,
    val uid: String
){
    constructor():this("","","")
}
