package com.kgamt.menu.app.presentation.ui.screens

import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kgamt.menu.app.R
import com.kgamt.menu.app.presentation.viewmodels.RequestViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuRequestScreen(
    viewModel: RequestViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSuccess: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val group by viewModel.group.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.getMenu()
    }
    LaunchedEffect(uiState.onRequestSuccess) {
        if(uiState.onRequestSuccess){
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Подача заявки")
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

                        Button(onClick = {
                            viewModel.clearError()
                            viewModel.getMenu()
                        }) {
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
                                text = "Меню на сегодня",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }


                        item {
                            ElevatedCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    Text(
                                        text = uiState.menu?.weekDay.orEmpty(),
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    HorizontalDivider()

                                    uiState.menu?.items?.forEach { item ->
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
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {

                                    Text(
                                        text = "Количество порций",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    OutlinedTextField(
                                        value = uiState.withoutSoup,
                                        onValueChange = viewModel::onWithoutChange,
                                        label = { Text("Без супа") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        )
                                    )

                                    OutlinedTextField(
                                        value = uiState.withSoup,
                                        onValueChange = viewModel::onWithChange,
                                        label = { Text("С супом") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        )
                                    )
                                }
                            }
                        }


                        item {
                            ElevatedCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    val total =
                                        (uiState.withSoup.toIntOrNull() ?: 0) +
                                                (uiState.withoutSoup.toIntOrNull() ?: 0)

                                    Text(
                                        text = "Всего порций: $total",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "Стоимость: ${uiState.menu?.cost ?: 0} ₽",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }


                        item {
                            Button(
                                onClick = {
                                    Log.d("request", "Нажата кнопка отправки")
                                    viewModel.saveRequest()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                    Text("Подать заявку")


                            }
                            if(!uiState.requestError.isNullOrEmpty()){
                                Text(
                                    text = uiState.requestError!!,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}