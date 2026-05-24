package com.kgamt.menu.app.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kgamt.menu.app.domain.models.MenuItemDto
import com.kgamt.menu.app.domain.repositories.ApiService

class DishPagingSource(private val api: ApiService) : PagingSource<Int, MenuItemDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MenuItemDto> {
        val page = params.key ?: 1
        return try {
            val response = api.getDishesPaginated(page, params.loadSize)
            LoadResult.Page(
                data = response.dishes,
                prevKey = if (page == 1) null else page - 1,
                nextKey = response.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MenuItemDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}