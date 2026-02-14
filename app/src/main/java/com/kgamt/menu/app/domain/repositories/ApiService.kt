package com.kgamt.menu.app.domain.repositories

import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/menu")
    suspend fun getMenu(@Query("date") date: String): MenuResponse

    @GET("api/dishes")
    suspend fun getDishes(): List<MenuItemDto>
}