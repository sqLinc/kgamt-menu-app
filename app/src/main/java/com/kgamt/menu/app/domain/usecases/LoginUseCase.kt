package com.kgamt.menu.app.domain.usecases

import android.util.Log
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import com.kgamt.menu.app.domain.models.AuthResult
import com.kgamt.menu.app.domain.models.LoginRequest
import com.kgamt.menu.app.domain.models.LoginResponse
import com.kgamt.menu.app.domain.repositories.ApiService
import com.kgamt.menu.app.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(username: String, password: String): AuthResult {

        if (username.isBlank() || password.isBlank()) {
            return AuthResult.Error.EmptyFields
        }

        if (password.length < 2) {
            return AuthResult.Error.WeakPassword
        }

        return try {
            val response = authRepository.login(username, password)

            AuthResult.Success(
                token = response.token,
                group = response.group
            )

        } catch (e: Exception) {
            AuthResult.Error.Network(e.message)
        }
    }
}