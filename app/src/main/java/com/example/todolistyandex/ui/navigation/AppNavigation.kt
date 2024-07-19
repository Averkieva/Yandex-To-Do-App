package com.example.todolistyandex.ui.navigation


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistyandex.ui.compose.settings.SettingsScreen
import com.example.todolistyandex.ui.compose.listoftask.ListOfTaskScreen
import com.example.todolistyandex.ui.fragment.CreateNewTaskScreen
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = "listOfTask"
    ) {
        composable(
            "settings",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
            }
        ) {
            SettingsScreen(navController = navController)
        }
        composable(
            "listOfTask",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
            }
        ) {
            val taskViewModel: ListOfTaskViewModel = hiltViewModel()
            ListOfTaskScreen(taskViewModel = taskViewModel, navController = navController)
        }
        composable(
            route = "createNewTask/{taskId}/{taskTitle}/{taskPriority}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType; defaultValue = -1 },
                navArgument("taskTitle") { type = NavType.StringType; defaultValue = "" },
                navArgument("taskPriority") { type = NavType.StringType; defaultValue = "NORMAL" }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
            }
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
