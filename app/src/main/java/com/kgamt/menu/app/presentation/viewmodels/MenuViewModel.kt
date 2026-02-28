package com.kgamt.menu.app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.MenuRepositoryImpl
import com.kgamt.menu.app.domain.models.DishCategory
import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse
import com.kgamt.menu.app.domain.repositories.MenuRepository
import com.kgamt.menu.app.presentation.ui.state.MenuScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepo: MenuRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(MenuScreenUiState())
    val uiState: StateFlow<MenuScreenUiState> = _uiState



    fun loadMenu() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isMenuLoading = true)
            try {
                val menu = menuRepo.getMenu()
                _uiState.value = _uiState.value.copy(
                    menu = menu,
                    isMenuLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMenu = e.toString(),
                    isMenuLoading = false

                )
            }
        }
    }

    fun loadDishes(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDishesLoading = true)
            try {
                val dishes = menuRepo.getDishes()
                _uiState.value = _uiState.value.copy(
                    dishes = dishes,
                    isDishesLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorDishes = e.toString(),
                    isDishesLoading = false
                )

            }
        }
    }

    fun retry(name: String){
        viewModelScope.launch {
            when (name) {
                "dishes" -> {
                    loadDishes()
                    _uiState.value = _uiState.value.copy(
                        errorDishes = null
                    )
                }
                "menu" -> {
                    loadMenu()
                    _uiState.value = _uiState.value.copy(
                        errorMenu = null
                    )
                }
            }
        }
    }

    fun onCategoryChange(category: DishCategory) {
        _uiState.value = _uiState.value.copy(
            category = category
        )
        Log.d("category_log", "Category has changed")
    }

}