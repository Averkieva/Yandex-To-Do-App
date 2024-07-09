package com.example.todolistyandex.ui.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.todolistyandex.data.network.NetworkReceiver
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.ui.navigation.AppNavigation
import com.example.todolistyandex.ui.theme.CustomTheme
import kotlinx.coroutines.launch

/**
 * Main activity class for the application, setting up the ToDoItemsRepository and
 * NetworkReceiver, registering network connectivity changes, and initializing the
 * Compose UI with custom theming and navigation.
 */

class MainActivity : ComponentActivity() {
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var repository: ToDoItemsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = (applicationContext as MyApplication).repository
        networkReceiver = NetworkReceiver {
            lifecycleScope.launch {
                repository.fetchTodoItems()
            }
        }
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        setContent {
            CustomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }
}



