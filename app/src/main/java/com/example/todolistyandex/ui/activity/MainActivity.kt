package com.example.todolistyandex.ui.activity

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.todolistyandex.R
import com.example.todolistyandex.data.repository.ToDoItemsRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }
}

class MyApplication : Application() {
    val repository: ToDoItemsRepository by lazy { ToDoItemsRepository() }
}