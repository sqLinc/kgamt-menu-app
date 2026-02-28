package com.kgamt.menu.app.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kgamt.menu.app.R
import com.kgamt.menu.app.domain.models.DishCategory
import com.kgamt.menu.app.presentation.routes.Destinations.Tabs
import com.kgamt.menu.app.presentation.ui.components.DishItem
import com.kgamt.menu.app.presentation.ui.components.ExpandableSection
import com.kgamt.menu.app.presentation.viewmodels.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    onDishDescription: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var selectedTab by remember { mutableStateOf(Tabs.DISHES) }
    var selectedCategory by remember { mutableIntStateOf(0) }

    val filteredList = remember(uiState.dishes, uiState.category) {
        if (uiState.category == DishCategory.ALL){
            uiState.dishes
        } else {
            uiState.dishes.filter { it.category == uiState.category }
        }
    }



    LaunchedEffect(Unit) {
        viewModel.loadMenu()
        viewModel.loadDishes()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Дополнительно",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        HorizontalDivider()
                        Text(
                            "Категории",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    itemsIndexed(DishCategory.entries.toTypedArray()) { index, category ->
                        NavigationDrawerItem(
                            label = { Text(category.displayName) },
                            selected = selectedCategory == index,
                            onClick = {
                                selectedCategory = index
                                viewModel.onCategoryChange(category)
                                Log.d("category_log", "Clicked on category: ${category}")

                            }
                        )
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(
                            "Прочее",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("Настройки") },
                            selected = false,
                            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                            onClick = { /* handle click */ }
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("Помощь и обратная связь") },
                            selected = false,
                            icon = { Icon(Icons.Default.Menu, contentDescription = null) },
                            onClick = { /* handle click */ }
                        )
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Столовая КГАМТ") },
                    actions = {
                        IconButton(onClick = { /*сделать диалоговое окно с переходом на сайт*/ }) {
                            Icon(
                                painterResource(R.drawable.kgamt_logo),
                                contentDescription = "Search",
                                tint = Color.Unspecified
                            )
                        }
                    }
                )
            },

            ) { padding ->
            Column(modifier = Modifier.padding(padding)) {

                PrimaryTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Tabs.entries.forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTab.ordinal == index,
                            onClick = { selectedTab = Tabs.entries.toTypedArray()[index] },
                            text = { Text(tab.label) },
                            icon = {
                                Icon(
                                    painter = painterResource(id = tab.icon),
                                    contentDescription = tab.label
                                )
                            },

                            )
                    }
                }


                when (selectedTab) {
                    Tabs.DISHES -> {
                        when {
                            uiState.isDishesLoading -> {
                                CircularProgressIndicator()
                            }

                            uiState.errorDishes != null -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Ошибка во время загрузки блюд: ${uiState.errorDishes}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Button(
                                        onClick = {viewModel.retry("dishes")}
                                    ) {
                                        Text("Повторить")
                                    }
                                }
                            }
                            else -> {
                                LazyColumn {
                                    items(filteredList) { item ->
                                        DishItem(item, onDishClick = { onDishDescription(item.id) })
                                    }
                                }

                            }
                        }

                    }

                    Tabs.MENU -> {
                        when {
                            uiState.isMenuLoading -> {
                                CircularProgressIndicator()
                            }

                            uiState.errorMenu != null -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Ошибка во время загрузки меню: ${uiState.errorMenu}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Button(
                                        onClick = {viewModel.retry("menu")}
                                    ) {
                                        Text("Повторить")
                                    }
                                }
                            }
                            else -> {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(uiState.menu) { menuDay ->
                                        ExpandableSection(menuDay)
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }


    }
}