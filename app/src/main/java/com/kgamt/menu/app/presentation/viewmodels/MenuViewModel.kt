package com.kgamt.menu.app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgamt.menu.app.data.MenuRepositoryImpl
import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse
import com.kgamt.menu.app.domain.repositories.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepo: MenuRepository
) : ViewModel(){

    private val _menu = MutableStateFlow<MenuResponse?>(null)
    private val _dishes = MutableStateFlow<List<MenuItemDto?>>(emptyList())

    val menu: StateFlow<MenuResponse?> = _menu
    val dishes: StateFlow<List<MenuItemDto?>> = _dishes

    fun loadMenu(date: String) {
        viewModelScope.launch {
            try {
                _menu.value = menuRepo.getMenu(date)
            } catch (e: Exception) {
                Log.d("MenuVM", "Error loading menu:", e)
            }
        }
    }

    fun loadDishes(){
        viewModelScope.launch {
            try {
                _dishes.value = menuRepo.getDishes()
            } catch (e: Exception) {
                Log.d("MenuVM", "Error loading dishes:", e)
            }
        }
    }

}