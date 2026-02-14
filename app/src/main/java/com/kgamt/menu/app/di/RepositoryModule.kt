package com.kgamt.menu.app.di

import com.kgamt.menu.app.data.MenuRepositoryImpl
import com.kgamt.menu.app.domain.repositories.ApiService
import com.kgamt.menu.app.domain.repositories.MenuRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun provideMenuRepository(
        impl: MenuRepositoryImpl
    ): MenuRepository

}