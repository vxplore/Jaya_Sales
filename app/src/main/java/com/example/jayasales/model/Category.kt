package com.example.jayasales.model

data class Category(
    val id: String,
    val name: String,
    val image: String? = null
){
    companion object{
        val All = Category("","All")
    }
}