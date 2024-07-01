package com.example.todolistyandex.ui.activity

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.ui.fragment.CreateNewTaskScreen
import com.example.todolistyandex.ui.fragment.ListOfTaskScreen
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModelFactory
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import com.example.todolistyandex.ui.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "listOfTask") {
        composable("listOfTask") {
            val context = LocalContext.current
            val repository = (context.applicationContext as MyApplication).repository
            val taskViewModel: ListOfTaskViewModel = viewModel(
                factory = ListOfTaskViewModelFactory(repository)
            )
            ListOfTaskScreen(taskViewModel = taskViewModel, navController = navController)
        }
        composable(
            route = "createNewTask/{taskId}/{taskTitle}/{taskPriority}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType; defaultValue = -1 },
                navArgument("taskTitle") { type = NavType.StringType; defaultValue = "" },
                navArgument("taskPriority") { type = NavType.StringType; defaultValue = "NORMAL" }
            )
        ) { backStackEntry ->
            val context = LocalContext.current
            val repository = (context.applicationContext as MyApplication).repository
            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(repository)
            )
            CreateNewTaskScreen(
                navController = navController,
                taskViewModel = taskViewModel,
                taskId = backStackEntry.arguments?.getInt("taskId"),
                taskTitle = backStackEntry.arguments?.getString("taskTitle"),
                taskPriority = backStackEntry.arguments?.getString("taskPriority"),

                )
        }
    }
}

class MyApplication : Application() {
    val repository: ToDoItemsRepository by lazy { ToDoItemsRepository() }
}