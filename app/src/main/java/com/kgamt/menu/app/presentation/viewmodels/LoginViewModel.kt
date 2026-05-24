package com.kgamt.menu.app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import com.kgamt.menu.app.domain.models.AuthResult
import com.kgamt.menu.app.domain.models.LoginRequest
import com.kgamt.menu.app.domain.usecases.LoginUseCase
import com.kgamt.menu.app.presentation.ui.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val settingsRepo: SettingsRepositoryImpl,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsernameChange(username: String){
        _uiState.value = _uiState.value.copy(
            username = username
        )
    }

    fun onPasswordChange(password: String){
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    fun login(username: String, password: String) {

        viewModelScope.launch {

            _uiState.value = LoginUiState(isLoading = true)

            val result = loginUseCase(username, password)

            when (result) {

                is AuthResult.Success -> {
                    _uiState.value = LoginUiState(success = true)
                }

                is AuthResult.Error -> {

                    val message = when (result) {

                        AuthResult.Error.EmptyFields ->
                            "Заполните все поля"

                        AuthResult.Error.WeakPassword ->
                            "Пароль должен быть минимум 8 символов"

                        AuthResult.Error.InvalidCredentials ->
                            "Неверный логин или пароль"

                        is AuthResult.Error.Network ->
                            "Ошибка сети: ${result.message}"
                    }

                    _uiState.value = LoginUiState(error = message)
                }
            }
        }
    }
}