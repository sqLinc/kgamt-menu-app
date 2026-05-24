package com.kgamt.menu.app.domain.models

data class DishPageResponse(
    val dishes: List<MenuItemDto>,
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long,
    val nextPage: Int?
)