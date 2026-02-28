package com.kgamt.menu.app.presentation.ui.state

import com.kgamt.menu.app.domain.models.DishCategory
import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse

data class MenuScreenUiState(
    val menu: List<MenuResponse> = emptyList(),
    val dishes: List<MenuItemDto> = emptyList(),
    val isMenuLoading: Boolean = false,
    val errorMenu: String? = null,
    val isDishesLoading: Boolean = false,
    val errorDishes: String? = null,
    val category: DishCategory = DishCategory.ALL
)
