package com.kgamt.menu.app.domain.repositories

import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse

interface MenuRepository {
    suspend fun getMenu(date: String): MenuResponse
    suspend fun getDishes(): List<MenuItemDto>
}