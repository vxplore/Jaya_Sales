package com.example.jayasales.model

data class AllBrandDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<Brand>
) {

    data class Brand(
        val uid: String,
        val name: String
    )
    companion object{
        val All = Brand("", "All")
    }
}