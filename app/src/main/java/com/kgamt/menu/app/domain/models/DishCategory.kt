package com.kgamt.menu.app.domain.models

enum class DishCategory(
    val displayName: String
) {
    DRINK("Напитки"),
    BAKERY("Выпечка"),
    FIRST("Первое"),
    SECOND("Второе"),
    SIDE("Гарнир"),
    SALAD("Салат"),
    ALL("Все блюда")
}