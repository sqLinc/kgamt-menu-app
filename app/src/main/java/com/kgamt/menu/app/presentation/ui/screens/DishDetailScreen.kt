package com.kgamt.menu.app.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kgamt.menu.app.presentation.viewmodels.DishDetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishDetailScreen(
    viewModel: DishDetailViewModel = hiltViewModel(),
    dishId: Long,
    onBackClick: () -> Unit = {}

) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDish(dishId)

    }

    uiState.dish?.let { dish ->
        Log.d("category_log", "Checking item category: ${dish.category}")
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.padding(top = 25.dp)) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""

                )
            }

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxSize().padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GlideImage(
                        model = dish.imageUrl,
                        contentDescription = "Dish image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 5.dp, bottom = 5.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = dish.name,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 40.sp
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Стоимость: ${dish.price} рублей",
                            fontWeight = FontWeight.Medium,
                            fontSize = 25.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Количество порции: ${dish.quantity} грамм",
                            fontWeight = FontWeight.Medium,
                            fontSize = 25.sp
                        )
                        Spacer(Modifier.height(30.dp))
                        Text(
                            text = "Каллорийность: ${dish.kcal}ккал/100гр",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Белки: ${dish.protein}гр",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Жиры: ${dish.fat}гр",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Углеводы: ${dish.carb}гр",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Категория: ${dish.category.displayName}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(5.dp))
                        Text(
                            text = dish.desc,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )

                    }





                }

            }

        }
    }



}