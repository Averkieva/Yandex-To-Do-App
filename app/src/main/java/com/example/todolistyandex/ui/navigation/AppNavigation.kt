package com.example.todolistyandex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistyandex.ui.activity.MyApplication
import com.example.todolistyandex.ui.compose.listoftask.ListOfTaskScreen
import com.example.todolistyandex.ui.fragment.CreateNewTaskScreen
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import com.example.todolistyandex.ui.viewmodel.ToDoViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "listOfTask") {
        composable("listOfTask") {
            val taskViewModel: ListOfTaskViewModel = hiltViewModel()
            ListOfTaskScreen(taskViewModel = taskViewModel, navController = navController)
        }
        composable(
            route = "createNewTask/{taskId}/{taskTitle}/{taskPriority}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType; defaultValue = -1 },
                navArgument("taskUuid") { type = NavType.StringType; defaultValue = "" },
                navArgument("taskTitle") { type = NavType.StringType; defaultValue = "" },
                navArgument("taskPriority") { type = NavType.StringType; defaultValue = "NORMAL" }
            )
        ) { backStackEntry ->
            val taskViewModel: TaskViewModel = hiltViewModel()
            CreateNewTaskScreen(
                navController = navController,
                taskViewModel = taskViewModel,
                taskId = backStackEntry.arguments?.getInt("taskId"),
                taskTitle = backStackEntry.arguments?.getString("taskTitle"),
                taskPriority = backStackEntry.arguments?.getString("taskPriority")
            )
        }
    }
}