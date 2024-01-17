package com.example.jayasales.model

data class AllCategory(
    val status: Boolean,
    val message: String,
    val data: List<Category>
) {
    data class Category(
        val uid: String,
        val name: String
    )

    companion object {
        val All = Category("", "All")
    }
}