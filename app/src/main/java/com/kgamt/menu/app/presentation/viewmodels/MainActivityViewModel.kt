package com.kgamt.menu.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepo: SettingsRepositoryImpl
) : ViewModel() {
    val token: Flow<String?> = settingsRepo.token
    val group: Flow<String?> = settingsRepo.group
}