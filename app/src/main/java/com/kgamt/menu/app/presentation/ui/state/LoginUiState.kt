package com.kgamt.menu.app.presentation.ui.state

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false

)
