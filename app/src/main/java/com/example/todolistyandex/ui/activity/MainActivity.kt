package com.example.todolistyandex.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.todolistyandex.data.settings.PreferencesManager
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.ui.navigation.AppNavigation
import com.example.todolistyandex.ui.theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity class for the application, setting up the ToDoItemsRepository and
 * NetworkReceiver, registering network connectivity changes, and initializing the
 * Compose UI with custom theming and navigation.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: ToDoItemsRepository

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themePreference = preferencesManager.themePreferenceFlow.collectAsState(initial = preferencesManager.themePreference).value
            CustomTheme(themePreference) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
                }
            }
        }

        lifecycleScope.launch {
            repository.retryPendingOperations()
        }
    }
    fun makeIntent(context: Context?): Intent {
        return Intent(context, MainActivity::class.java)
    }
}



