package com.kgamt.menu.app.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kgamt.menu.app.presentation.ui.components.ExpandableRequests
import com.kgamt.menu.app.presentation.ui.components.ExpandableSection
import com.kgamt.menu.app.presentation.viewmodels.RequestHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestHistory(
    viewModel: RequestHistoryViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    val group by viewModel.group.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllRequests(group!!)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("История заявок")
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
                uiState.isLoading == true -> {
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
                                viewModel.getAllRequests(group!!)
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
                            Text(
                                text = group ?: "Группа не найдена",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        }
                        items(uiState.requests!!) { request ->
                            ExpandableRequests(request)
                        }
                    }
                }
            }
        }
    }
}