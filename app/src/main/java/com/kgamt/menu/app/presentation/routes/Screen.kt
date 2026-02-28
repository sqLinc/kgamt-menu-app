package com.kgamt.menu.app.presentation.routes

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object MenuList : Screen("menu")
    object Dish : Screen("dish")
}