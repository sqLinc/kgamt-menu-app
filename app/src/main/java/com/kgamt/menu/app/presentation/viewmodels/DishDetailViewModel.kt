package com.kgamt.menu.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.domain.repositories.MenuRepository
import com.kgamt.menu.app.presentation.ui.state.DishDetailUiState
import com.kgamt.menu.app.presentation.ui.state.MenuScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishDetailViewModel @Inject constructor(
    private val menuRepo: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DishDetailUiState())
    val uiState: StateFlow<DishDetailUiState> = _uiState

    fun getDish(id: Long){
        viewModelScope.launch {
            val dish = menuRepo.getDish(id)
            _uiState.value = _uiState.value.copy(
                dish = dish
            )

        }

    }
}