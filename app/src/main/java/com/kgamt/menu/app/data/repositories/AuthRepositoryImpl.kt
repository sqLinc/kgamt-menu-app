package com.kgamt.menu.app.data.repositories

import com.kgamt.menu.app.domain.models.LoginRequest
import com.kgamt.menu.app.domain.models.LoginResponse
import com.kgamt.menu.app.domain.repositories.ApiService
import com.kgamt.menu.app.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val settingsRepo: SettingsRepositoryImpl
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse {

        val response = api.login(LoginRequest(username, password))

        settingsRepo.setToken(response.token)
        settingsRepo.setGroup(response.group)

        return response
    }
}