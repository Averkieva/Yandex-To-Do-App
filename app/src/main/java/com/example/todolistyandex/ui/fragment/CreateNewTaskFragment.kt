package com.example.todolistyandex.ui.fragment

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.compose.createnewtask.DeleteButton
import com.example.todolistyandex.ui.compose.createnewtask.TaskHeader
import com.example.todolistyandex.ui.compose.createnewtask.TaskInputField
import com.example.todolistyandex.ui.compose.createnewtask.TaskPriorityAndDate
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import java.util.Calendar

@Composable
fun CreateNewTaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    taskId: Int?,
    taskTitle: String?,
    taskPriority: String?,
) {
    val context = LocalContext.current
    var taskText by remember { mutableStateOf(taskTitle?.trim() ?: "") }
    var priority by remember { mutableStateOf(taskPriority?.let { ListOfTaskStatus.valueOf(it) } ?: ListOfTaskStatus.NORMAL) }
    var isPriorityDropdownExpanded by remember { mutableStateOf(false) }
    var deadlineComplete by remember { mutableStateOf("") }
    var isDeadlineSet by remember { mutableStateOf(false) }
    val fetchError by taskViewModel.fetchError.collectAsState()
    val scaffoldState = rememberScaffoldState()

    val priorityItems = listOf(ListOfTaskStatus.HIGH, ListOfTaskStatus.NORMAL, ListOfTaskStatus.LOW)

    if (taskTitle.isNullOrEmpty()) return

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CustomDatePickerDialog,
        { _, selectedYear, selectedMonth, selectedDay ->
            val date = "%02d.%02d.%d".format(selectedDay, selectedMonth + 1, selectedYear)
            deadlineComplete = date
            taskViewModel.setDeadline(date)
        }, year, month, day
    )

    LaunchedEffect(fetchError) {
        fetchError?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
            taskViewModel.clearFetchError()
        }
    }

    LaunchedEffect(Unit) {
        if (taskId != null && taskId != -1) {
            taskViewModel.getTaskById(taskId).observe(context as LifecycleOwner) { task ->
                task?.let {
                    taskText = it.text
                    priority = it.priority
                    deadlineComplete = it.deadlineComplete ?: ""
                    taskViewModel.setTask(it)
                }
            }
        } else {
            taskViewModel.setTask(ToDoItem(id = 0, text = "", priority = ListOfTaskStatus.NORMAL))
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = CustomTheme.colors.backPrimary,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                TaskHeader(navController, taskText, taskViewModel)
                TaskInputField(taskText) { taskText = it }
                TaskPriorityAndDate(
                    priority, isPriorityDropdownExpanded, deadlineComplete, isDeadlineSet, priorityItems,
                    { isPriorityDropdownExpanded = it },
                    { priority = it; taskViewModel.setPriority(it) },
                    { isDeadlineSet = it; if (it) datePickerDialog.show() else { deadlineComplete = ""; taskViewModel.setDeadline("") } }
                )
                DeleteButton(taskText, taskViewModel, navController)
            }
        }
    )
}
