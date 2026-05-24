package com.kgamt.menu.app.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kgamt.menu.app.domain.models.FoodRequest
import com.kgamt.menu.app.domain.models.FoodResponseDto
import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.models.MenuResponse
import com.kgamt.menu.app.domain.models.RequestsHistoryDto
import com.kgamt.menu.app.domain.models.TestResponse
import com.kgamt.menu.app.domain.repositories.ApiService
import com.kgamt.menu.app.domain.repositories.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MenuRepository {
    override suspend fun getMenu(): List<MenuResponse> {
        return api.getMenu()
    }

    override suspend fun getDishesPaginated(): Flow<PagingData<MenuItemDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DishPagingSource(api) }
        ).flow
    }

    override suspend fun getDish(id: Long): MenuItemDto {
        return api.getDish(id)
    }

    override suspend fun getTodayMenu(): MenuResponse {
        return api.getTodayMenu()
    }

    override suspend fun sendRequest(request: FoodRequest): Boolean {
        Log.d("request", "Запуск функции из api")
        return api.sendRequest(request)
    }

    override suspend fun getTodayRequest(group: String): FoodResponseDto {
        return api.getTodayRequest(group)
    }

    override suspend fun sendUpdated(request: FoodResponseDto): Boolean {
        return api.sendUpdated(request)
    }

    override suspend fun getAll(group: String): List<RequestsHistoryDto> {
        return api.getAll(group)
    }

    override suspend fun test() : TestResponse{
        return api.test()
    }
}