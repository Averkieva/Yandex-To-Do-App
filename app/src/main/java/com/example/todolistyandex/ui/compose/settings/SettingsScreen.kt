package com.example.todolistyandex.ui.compose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolistyandex.ui.viewmodel.SettingsViewModel
import com.example.todolistyandex.data.settings.ThemePreference
import com.example.todolistyandex.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themePreference by viewModel.themePreference.collectAsState()

    CustomTheme(themePreference) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Settings",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = CustomTheme.colors.labelPrimary
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CustomTheme.colors.backPrimary,
                        titleContentColor = CustomTheme.colors.labelPrimary,
                        actionIconContentColor = CustomTheme.colors.labelPrimary,
                        navigationIconContentColor = CustomTheme.colors.labelPrimary
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(CustomTheme.colors.backPrimary)
                    .padding(16.dp)
            ) {
                RadioButtonGroup(
                    options = ThemePreference.values().toList(),
                    selectedOption = themePreference,
                    onOptionSelected = { viewModel.updateThemePreference(it) }
                )
            }
        }
    }
}

@Composable
fun <T> RadioButtonGroup(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = CustomTheme.colors.colorBlue,
                        unselectedColor = CustomTheme.colors.labelTertiary,
                        disabledSelectedColor = CustomTheme.colors.labelDisable,
                        disabledUnselectedColor = CustomTheme.colors.labelDisable
                    )
                )
                Text(
                    text = option.toString(),
                    modifier = Modifier.padding(start = 8.dp),
                    color = CustomTheme.colors.labelPrimary
                )
            }
        }
    }
}