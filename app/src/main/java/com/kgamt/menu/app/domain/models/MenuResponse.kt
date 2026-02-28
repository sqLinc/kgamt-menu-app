package com.kgamt.menu.app.domain.models

data class MenuResponse(
    val date: String,
    val weekDay: String,
    val items: List<MenuItemDto>
)
