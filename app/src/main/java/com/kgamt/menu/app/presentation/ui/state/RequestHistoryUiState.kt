package com.kgamt.menu.app.presentation.ui.state

import com.kgamt.menu.app.domain.models.RequestsHistoryDto

data class RequestHistoryUiState(
    val isLoading: Boolean? = true,
    val error: String? = null,
    val requests: List<RequestsHistoryDto>? = null
)
