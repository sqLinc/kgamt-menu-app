package com.kgamt.menu.app.domain.models

sealed class AuthResult {
    data class Success(val token: String, val group: String) : AuthResult()

    sealed class Error : AuthResult() {
        data object WeakPassword : Error()
        data object EmptyFields : Error()
        data object InvalidCredentials : Error()
        data class Network(val message: String?) : Error()
    }
}
