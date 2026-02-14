package com.kgamt.menu.app.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kgamt.menu.app.presentation.viewmodels.MenuViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel()
) {
    val menu by viewModel.menu.collectAsState()
    val dishes by viewModel.dishes.collectAsState()

    LaunchedEffect(Unit) {
        //viewModel.loadMenu("2026-02-12")
        viewModel.loadDishes()
    }

//    menu?.let { menuResponse ->
//        Column {
//            Text("Menu for ${menuResponse.date}")
//            LazyColumn {
//                items(menuResponse.items) { item ->
//                    Text("${item.name} - ${item.price}")
//                }
//            }
//        }
//    }

    dishes.let { dishesResponse ->
        Column {
            Text("Dishes:")
            LazyColumn {
                items(dishesResponse) { item ->
                    Text("${item!!.name} - ${item.price}")
                }
            }
        }
    }






}