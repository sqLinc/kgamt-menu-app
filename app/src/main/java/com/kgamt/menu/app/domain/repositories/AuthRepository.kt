package com.kgamt.menu.app.domain.repositories

import com.kgamt.menu.app.domain.models.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse
}