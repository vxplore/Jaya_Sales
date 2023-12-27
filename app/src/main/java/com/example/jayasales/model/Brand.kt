package com.example.jayasales.model

data class Brand(
    val id: String,
    val name: String,
    val categories: List<Category> = emptyList(),
    val image: String? = null,
){
    companion object{
        val All = Brand("", "All")
    }
}