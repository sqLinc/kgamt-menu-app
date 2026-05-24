package com.kgamt.menu.app.domain.models

data class FoodResponseDto(
    val id: Long? = null,
    val group: String? = null,
    val withSoup: Int = 0,
    val withoutSoup: Int = 0,
    val date: String? = null,
    val isConfirmed: Boolean? = null,
    val totalCost: Int? = null,
    val isPaid: Boolean? = null,
    val items: List<MenuItemDto>? = null,
    val costPerServing: Int? = null
)
