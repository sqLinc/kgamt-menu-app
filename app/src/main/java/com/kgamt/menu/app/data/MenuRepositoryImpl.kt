package com.kgamt.menu.app.data

import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse
import com.kgamt.menu.app.domain.repositories.ApiService
import com.kgamt.menu.app.domain.repositories.MenuRepository
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MenuRepository{
    override suspend fun getMenu(date: String): MenuResponse {
        return api.getMenu(date)
    }

    override suspend fun getDishes(): List<MenuItemDto> {
        return api.getDishes()
    }
}