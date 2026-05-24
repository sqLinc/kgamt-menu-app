package com.kgamt.menu.app.domain.models

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val group: String
)