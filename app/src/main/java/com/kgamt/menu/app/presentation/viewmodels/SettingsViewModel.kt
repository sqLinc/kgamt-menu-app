package com.kgamt.menu.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepo: SettingsRepositoryImpl,
) : ViewModel() {

    val isDarkTheme = settingsRepo.isDarkThemeFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val language = settingsRepo.language.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "en"
    )

    fun setDark(value: Boolean) {
        viewModelScope.launch {
            settingsRepo.setDarkTheme(value)
        }
    }

    fun setLanguage(value: String) {
        viewModelScope.launch {
            settingsRepo.setLanguage(value)
        }
    }

    fun logOut(){
        viewModelScope.launch {
            settingsRepo.deleteToken()
            settingsRepo.deleteGroup()
        }
    }

}