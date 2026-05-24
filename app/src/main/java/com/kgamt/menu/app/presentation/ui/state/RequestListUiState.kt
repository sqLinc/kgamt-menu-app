package com.kgamt.menu.app.presentation.ui.state

import com.kgamt.menu.app.domain.models.FoodRequest
import com.kgamt.menu.app.domain.models.FoodResponseDto
import com.kgamt.menu.app.domain.models.MenuResponse

data class RequestListUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val todayRequest: FoodResponseDto? = null,
    val requestList: List<FoodResponseDto>? = null,
    val rememberWith: Int? = null,
    val rememberWithout: Int? = null,
    val updateError: String? = null,
    val onRequestSuccess: Boolean? = null,
    val rememberCost: Int? = null,

    val totalCost: Int = 0,
    val totalCount: Int = 0


    )
