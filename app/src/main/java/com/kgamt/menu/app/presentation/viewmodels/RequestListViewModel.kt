package com.kgamt.menu.app.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.repositories.SettingsRepositoryImpl
import com.kgamt.menu.app.domain.models.FoodResponseDto
import com.kgamt.menu.app.domain.repositories.MenuRepository
import com.kgamt.menu.app.presentation.ui.state.RequestListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class RequestListViewModel @Inject constructor(
    private val settingsRepo: SettingsRepositoryImpl,
    private val menuRepo: MenuRepository
) : ViewModel(){

    val group: StateFlow<String?> = settingsRepo.group
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _uiState = MutableStateFlow(RequestListUiState())
    val uiState: StateFlow<RequestListUiState> = _uiState

    val today = LocalDate.now()
    val formatted = DateTimeFormatter.ofPattern("d MMMM yyyy", java.util.Locale("ru"))

    val formattedDate = today.format(formatted)
    val weekDay = today.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale("ru"))

    fun clearError(){
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }

    fun getRequests(group: String){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            try {
                val request = menuRepo.getTodayRequest(group)
                if (request == null){
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Меню на этот день отсутствует!"
                    )
                } else{
                    _uiState.value = _uiState.value.copy(
                        todayRequest = request,
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

    fun onWithoutChange(withoutSoup: String) {
        val value = withoutSoup.toIntOrNull() ?: 0
        val current = _uiState.value.todayRequest ?: return

        val updated = current.copy(withoutSoup = value)

        _uiState.value = _uiState.value.copy(
            todayRequest = recalc(updated)
        )
    }
    fun onWithChange(withSoup: String) {
        val value = withSoup.toIntOrNull() ?: 0
        val current = _uiState.value.todayRequest ?: return

        val updated = current.copy(withSoup = value)

        _uiState.value = _uiState.value.copy(
            todayRequest = recalc(updated)
        )
    }

    fun updateRequest(){
        viewModelScope.launch {
            try {
                val request = FoodResponseDto(
                    id = _uiState.value.todayRequest!!.id,
                    group = _uiState.value.todayRequest!!.group,
                    withoutSoup = _uiState.value.todayRequest!!.withoutSoup,
                    withSoup = _uiState.value.todayRequest!!.withSoup,
                    isConfirmed = _uiState.value.todayRequest!!.isConfirmed,
                    isPaid = _uiState.value.todayRequest!!.isPaid,
                    totalCost = (_uiState.value.todayRequest!!.withSoup * uiState.value.todayRequest!!.costPerServing!!) + (uiState.value.todayRequest!!.withoutSoup * (uiState.value.todayRequest!!.costPerServing!! - 10)),
                    items = _uiState.value.todayRequest!!.items,
                    date = _uiState.value.todayRequest!!.date,
                    costPerServing = _uiState.value.todayRequest!!.costPerServing
                )

                val updated = menuRepo.sendUpdated(request)
                _uiState.value = _uiState.value.copy(
                    onRequestSuccess = updated,
                    isLoading = false
                )

            } catch (e: Exception){

            }
        }
    }

    fun onRememberSoup(){
        _uiState.value = _uiState.value.copy(
            rememberWith = _uiState.value.todayRequest!!.withSoup,
            rememberWithout = _uiState.value.todayRequest!!.withoutSoup
        )
    }

    fun onCancel(){
        val current = _uiState.value.todayRequest ?: return

        _uiState.value = _uiState.value.copy(
            todayRequest = current.copy(
                withSoup = _uiState.value.rememberWith!!,
                withoutSoup = _uiState.value.rememberWithout!!,
            ),
            updateError = null
        )
    }

    fun onError(m: String){
        _uiState.value = _uiState.value.copy(
            updateError = m
        )
    }

    private fun recalc(request: FoodResponseDto): FoodResponseDto {
        val with = request.withSoup
        val without = request.withoutSoup
        val price = request.costPerServing ?: 0

        val totalCost = (with * price) + (without * (price - 10))

        return request.copy(
            totalCost = totalCost
        )
    }




}