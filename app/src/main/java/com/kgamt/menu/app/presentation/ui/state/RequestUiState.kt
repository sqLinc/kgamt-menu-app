package com.kgamt.menu.app.presentation.ui.state

import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse

data class RequestUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val withoutSoup: String = "0",
    val withSoup: String = "0",
    val menu: MenuResponse? = null,
    val onRequestSuccess: Boolean = false,
    val requestError: String? = null
)
