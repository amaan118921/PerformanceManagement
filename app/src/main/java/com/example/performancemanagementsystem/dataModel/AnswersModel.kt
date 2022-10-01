package com.example.performancemanagementsystem.dataModel

data class AnswersModel(
    val memberId : String,
    val reponses : HashMap<String,List<String>>
)
{
    constructor():this("",HashMap())
}