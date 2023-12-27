package com.example.jayasales.model
data class Product(
    val id : Int,
    val name : String,
    val brandDetails : BrandDetail,
    val categoryDetails : CategoryDetail,
    val type : String,
    val mrp : Float,
    val sellValue : String,
    val quantity : Long,
    val discount : Int
) {
    val names: List<String>
        get() = listOf(
            name,
            type
        )
}

data class CategoryDetail(
    val id : String,
    val name: String
)
data class BrandDetail(
    val id : String,
    val name: String
)