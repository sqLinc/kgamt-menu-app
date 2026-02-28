package com.kgamt.menu.app.domain.models

data class MenuItemDto(
    val id: Long = 0,
    val name: String,
    val price: Int,
    val quantity: Int,
    val kcal: Int,
    val protein: Int,
    val fat: Int,
    val carb: Int,
    val desc: String,
    val category: DishCategory,
    var imageUrl: String

)
