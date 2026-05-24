package com.kgamt.menu.app.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kgamt.menu.app.R
import com.kgamt.menu.app.presentation.ui.components.LangDropDownMenu
import com.kgamt.menu.app.presentation.viewmodels.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit = {},
    language: String,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isDarkTheme by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.isDarkTheme.collectLatest { value ->
            isDarkTheme = value
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
                LangDropDownMenu(
                    language,
                    onOptionSelected = { selected ->
                        scope.launch {
                            viewModel.setLanguage(selected)
                            Log.d("language", selected)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_theme),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { newValue ->
                        isDarkTheme = newValue
                        scope.launch {
                            viewModel.setDark(newValue)
                        }
                    }
                )
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedButton(
                onClick = {
                    viewModel.logOut()
                    onLogoutClick()
                          },
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                Text("Выйти из аккаунта")
            }
        }
    }
}