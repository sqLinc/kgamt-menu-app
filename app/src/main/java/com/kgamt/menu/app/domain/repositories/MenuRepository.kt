package com.kgamt.menu.app.domain.repositories

import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse

interface MenuRepository {
    suspend fun getMenu(): List<MenuResponse>
    suspend fun getDishes(): List<MenuItemDto>
    suspend fun getDish(id: Long): MenuItemDto
}