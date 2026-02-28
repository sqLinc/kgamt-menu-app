package com.kgamt.menu.app.domain.repositories

import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/menu/full")
    suspend fun getMenu() : List<MenuResponse>

    @GET("api/dishes")
    suspend fun getDishes(): List<MenuItemDto>

    @GET("api/dishes/{id}")
    suspend fun getDish(
        @Path("id") id: Long
    ): MenuItemDto
}