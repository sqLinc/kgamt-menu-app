package com.kgamt.menu.app.presentation.ui.screens

import android.R
import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kgamt.menu.app.presentation.viewmodels.RequestListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestList(
    viewModel: RequestListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onEditRequest: () -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var updateButton by rememberSaveable { mutableStateOf(true) }

    val uiState by viewModel.uiState.collectAsState()
    val group by viewModel.group.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.getRequests(group!!)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Заявка на сегодня")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error!!,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                viewModel.clearError()
                                viewModel.getRequests(group!!)
                            }
                        ) {
                            Text("Повторить")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = "${viewModel.formattedDate}, ${viewModel.weekDay}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = group ?: "Группа не найдена",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                )
                            }

                        }
                        item {
                            Text(
                                text = "Заказ на сегодня",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }

                        item {
                            ElevatedCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {

                                    val total = (uiState.todayRequest?.withSoup ?: 0) + (uiState.todayRequest?.withoutSoup ?: 0)

                                    Text(
                                        text = "Всего порций: $total",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Стоимость: ${uiState.todayRequest!!.totalCost} ₽",
                                        style = MaterialTheme.typography.titleMedium
                                    )


                                    HorizontalDivider()

                                    uiState.todayRequest?.items?.forEach { item ->
                                        Text(
                                            text = "${stringResource(item.category.displayName)}: ${item.name}",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            ElevatedCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    if (uiState.todayRequest?.isConfirmed!!){
                                        Text(
                                            text = "Статус заказа: Подтверждено",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Green
                                        )
                                    } else {
                                        Text(
                                            text = "Статус заказа: Не подтверждено",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Red
                                        )
                                    }

                                    if (uiState.todayRequest?.isPaid!!){
                                        Text(
                                            text = "Статус оплаты: Оплачено ",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Green
                                        )
                                    } else {
                                        Text(
                                            text = "Статус оплаты: Не оплачено ",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Red
                                        )
                                    }




                                }
                            }
                        }
                        uiState.todayRequest?.let { request ->

                            request.isConfirmed?.let {
                                if (!it) {
                                    item {
                                        Button(
                                            onClick = {
                                                expanded = true
                                                viewModel.onRememberSoup()
                                                      },
                                            modifier = Modifier.fillMaxWidth(),
                                            enabled = !expanded
                                        ) {
                                            Text("Редактировать заявку")
                                        }
                                    }

                                    item {
                                        AnimatedVisibility(visible = expanded) {
                                            ElevatedCard {
                                                Column(
                                                    modifier = Modifier.padding(16.dp),
                                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                                ) {
                                                    Text(
                                                        text = "Количество порций",
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                    OutlinedTextField(
                                                        value = request.withoutSoup.toString(),
                                                        onValueChange = { viewModel.onWithoutChange(it) },
                                                        label = { Text("Без супа") },
                                                        modifier = Modifier.fillMaxWidth(),
                                                        singleLine = true,
                                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                    )

                                                    OutlinedTextField(
                                                        value = request.withSoup.toString(),
                                                        onValueChange = { viewModel.onWithChange(it) },
                                                        label = { Text("С супом") },
                                                        modifier = Modifier.fillMaxWidth(),
                                                        singleLine = true,
                                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                                    )

                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        OutlinedButton(
                                                            onClick = { viewModel.updateRequest() },
                                                            enabled = updateButton
                                                        ) {
                                                            Text("Обновить заявку")
                                                        }
                                                        OutlinedButton(
                                                            onClick = {
                                                                viewModel.onCancel()
                                                                expanded = false
                                                            }
                                                        ) {
                                                            Text("Отмена")
                                                        }
                                                    }
                                                    if(!uiState.updateError.isNullOrEmpty()){
                                                        Text(
                                                            text = uiState.updateError!!,
                                                            color = Color.Red,
                                                            textAlign = TextAlign.Center
                                                        )
                                                        Spacer(modifier = Modifier.height(10.dp))
                                                    }
                                                    if(uiState.todayRequest!!.withoutSoup <= 0 && uiState.todayRequest!!.withSoup <= 0 ){
                                                        updateButton = false
                                                        viewModel.onError("Значения порций не может быть меньше или равно 0!")
                                                    } else {
                                                        updateButton = true
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


            }

        }
    }
}


