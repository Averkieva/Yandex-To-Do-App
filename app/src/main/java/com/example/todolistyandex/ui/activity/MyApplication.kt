package com.example.todolistyandex.ui.activity

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.todolistyandex.data.network.NetworkReceiver
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.data.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Application class for initializing the ToDoItemsRepository and starting periodic
 * updates of the ToDoList when the application is created.
 */
@HiltAndroidApp
class MyApplication : Application() {
    lateinit var database: AppDatabase
        private set

    @Inject
    lateinit var repository: ToDoItemsRepository

    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-database"
        ).build()

        repository.startPeriodicUpdate(this)

        networkReceiver = NetworkReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)

        GlobalScope.launch {
            repository.retryPendingOperations()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}



