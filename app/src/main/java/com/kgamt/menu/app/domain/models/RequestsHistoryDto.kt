package com.kgamt.menu.app.domain.models

import java.time.LocalDate

data class RequestsHistoryDto(
    val month: String? = null,
    val requests: List<FoodResponseDto>? = null
)
