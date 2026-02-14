package com.kgamt.menu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kgamt.menu.app.presentation.routes.Screen
import com.kgamt.menu.app.presentation.ui.screens.LoginScreen
import com.kgamt.menu.app.presentation.ui.screens.MenuScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.MenuList.route
            ) {
                composable(Screen.Login.route) {
                    LoginScreen()
                }
                composable(Screen.MenuList.route) {
                    MenuScreen()
                }
            }

        }
    }
}


