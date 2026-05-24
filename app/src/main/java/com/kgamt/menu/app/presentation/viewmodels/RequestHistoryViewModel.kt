package com.kgamt.menu.app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import com.kgamt.menu.app.domain.repositories.MenuRepository
import com.kgamt.menu.app.presentation.ui.state.RequestHistoryUiState
import com.kgamt.menu.app.presentation.ui.state.RequestListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestHistoryViewModel @Inject constructor(
    private val settingsRepo: SettingsRepositoryImpl,
    private val menuRepo: MenuRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(RequestHistoryUiState())
    val uiState: StateFlow<RequestHistoryUiState> = _uiState

    val group: StateFlow<String?> = settingsRepo.group
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    fun clearError(){
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }

    fun getAllRequests(group: String){
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            try {
                val requests = menuRepo.getAll(group)
                if (requests == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не удалось загрузить историю заявок"
                    )
                } else{
                    _uiState.value = _uiState.value.copy(
                        requests = requests,
                        isLoading = false
                    )
                }


            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
                Log.e("history", e.message!!)
            }
        }
    }

    fun formatMonth(month: String): String {
        val (year, m) = month.split("-")
        val monthName = when (m.toInt()) {
            1 -> "Январь"
            2 -> "Февраль"
            3 -> "Март"
            4 -> "Апрель"
            5 -> "Май"
            6 -> "Июнь"
            7 -> "Июль"
            8 -> "Август"
            9 -> "Сентябрь"
            10 -> "Октябрь"
            11 -> "Ноябрь"
            12 -> "Декабрь"
            else -> ""
        }
        return "$monthName $year"
    }

}