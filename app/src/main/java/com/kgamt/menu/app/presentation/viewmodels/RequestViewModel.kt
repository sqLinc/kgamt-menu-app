package com.kgamt.menu.app.presentation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import com.kgamt.menu.app.domain.models.FoodRequest
import com.kgamt.menu.app.domain.repositories.MenuRepository
import com.kgamt.menu.app.presentation.ui.state.MenuScreenUiState
import com.kgamt.menu.app.presentation.ui.state.RequestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class RequestViewModel @Inject constructor(
    private val menuRepo: MenuRepository,
    private val settingsRepo: SettingsRepositoryImpl
) : ViewModel() {

    val group: StateFlow<String?> = settingsRepo.group
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState

    val today: LocalDate = LocalDate.now()
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))

    val formattedDate = today.format(formatter)
    val weekDay = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))

    fun getMenu(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            try {
                val menu = menuRepo.getTodayMenu()
                if (menu == null){
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Меню на этот день отсутствует!"
                    )
                } else{
                    _uiState.value = _uiState.value.copy(
                        menu = menu,
                        isLoading = false
                    )

                }

            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun clearError(){
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }

    fun onWithoutChange(withoutSoup: String){
        _uiState.value = _uiState.value.copy(
            withoutSoup = withoutSoup
        )
    }
    fun onWithChange(soup: String){
        _uiState.value = _uiState.value.copy(
            withSoup = soup
        )
    }

    fun saveRequest() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        val today = LocalDate.now()

        viewModelScope.launch {
            try {

                val withSoup = uiState.value.withSoup.toInt()
                val withoutSoup = uiState.value.withoutSoup.toInt()

                if (withSoup == 0 && withoutSoup == 0) {
                    _uiState.value = _uiState.value.copy(
                        requestError = "Общее количество порций равно 0",
                        isLoading = false
                    )
                    return@launch
                }

                val menu = uiState.value.menu ?: run {
                    _uiState.value = _uiState.value.copy(
                        requestError = "Нет меню",
                        isLoading = false
                    )
                    return@launch
                }

                val cost = (withSoup * menu.cost) + (withoutSoup * (menu.cost - 10))

                val food = FoodRequest(
                    group = group.value,
                    withSoup = withSoup,
                    withoutSoup = withoutSoup,
                    date = today.toString(),
                    isConfirmed = false,
                    totalCost = cost,
                    isPaid = false,
                    items = menu.items,
                    costPerServing = menu.cost
                )

                val request = menuRepo.sendRequest(food)

                _uiState.value = _uiState.value.copy(
                    onRequestSuccess = request,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    requestError = e.message,
                    isLoading = false
                )
            }
        }
    }


}