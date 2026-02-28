package com.kgamt.menu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kgamt.menu.app.presentation.routes.Screen
import com.kgamt.menu.app.presentation.ui.screens.DishDetailScreen
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
                    MenuScreen(
                        onDishDescription = { dishId ->
                            navController.navigate("${Screen.Dish.route}/$dishId")
                        }
                    )
                }

                composable(
                    route = "${Screen.Dish.route}/{dishId}",
                    arguments = listOf(
                        navArgument("dishId") {
                            type = NavType.LongType
                        }
                    )
                ) { backStackEntry ->
                    val dishId = backStackEntry.arguments?.getLong("dishId")
                    DishDetailScreen(
                        dishId = dishId!!,
                        onBackClick = {
                            navController.navigate(Screen.MenuList.route)
                        }
                    )
                }
            }

        }
    }
}


