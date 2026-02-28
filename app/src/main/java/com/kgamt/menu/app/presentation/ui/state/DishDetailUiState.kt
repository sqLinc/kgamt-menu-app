package com.kgamt.menu.app.presentation.ui.state

import com.kgamt.menu.app.domain.models.MenuItemDto

data class DishDetailUiState(
    val dish: MenuItemDto? = null
)
