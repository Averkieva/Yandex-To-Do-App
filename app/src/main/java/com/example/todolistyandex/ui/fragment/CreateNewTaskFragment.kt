package com.example.todolistyandex.ui.fragment

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.theme.BlueDark
import com.example.todolistyandex.ui.theme.BlueLight
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
    var priority by remember {
        mutableStateOf(taskPriority?.let { ListOfTaskStatus.valueOf(it) }
            ?: ListOfTaskStatus.NORMAL)
    }
    var isPriorityDropdownExpanded by remember { mutableStateOf(false) }
    var deadlineComplete by remember { mutableStateOf("") }
    var isDeadlineSet by remember { mutableStateOf(false) }


    val priorityItems = listOf(
        ListOfTaskStatus.HIGH,
        ListOfTaskStatus.NORMAL,
        ListOfTaskStatus.LOW
    )

    if (taskTitle.isNullOrEmpty()) {
        return
    }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CustomDatePickerDialog,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val date = "%02d.%02d.%d".format(selectedDay, selectedMonth + 1, selectedYear)
            deadlineComplete = date
            taskViewModel.setDeadline(date)
        }, year, month, day
    )

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CustomTheme.colors.backPrimary)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(CustomTheme.colors.backPrimary)
        ) {
            val (closeButton, saveButton, cardView, priorityLabel, priorityText, priorityDropdown, dateSwitch, divider, dateLabel, dateSetText, divider2, deleteButton, deleteText) = createRefs()

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.constrainAs(closeButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = CustomTheme.colors.labelPrimary
                )
            }

            TextButton(
                onClick = {
                    if (taskText.isNotEmpty()) { // Only save if taskText is not empty
                        taskViewModel.setText(taskText)
                        taskViewModel.saveToDoItem()
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.constrainAs(saveButton) {
                    end.linkTo(parent.end)
                    top.linkTo(closeButton.top)
                    bottom.linkTo(closeButton.bottom)
                }
            ) {

                Text(text = stringResource(R.string.save_task), color = BlueDark, fontSize = 14.sp)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(top = 24.dp)
                    .constrainAs(cardView) {
                        top.linkTo(closeButton.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = CustomTheme.colors.backSecondary,
                    contentColor = CustomTheme.colors.labelPrimary
                )
            ) {

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {

                    TextField(
                        placeholder = {

                            Text(
                                text = stringResource(R.string.what_to_do),
                                color = CustomTheme.colors.labelTertiary,
                                fontSize = 16.sp
                            )
                        },
                        value = taskText,
                        onValueChange = { taskText = it },
                        modifier = Modifier
                            .fillMaxSize(),
                        textStyle = LocalTextStyle.current.copy(
                            color = CustomTheme.colors.labelPrimary,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = CustomTheme.colors.labelPrimary,
                            placeholderColor = CustomTheme.colors.labelTertiary,
                            backgroundColor = CustomTheme.colors.backSecondary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = BlueDark,
                        ),
                    )
                }
            }

            Text(
                text = stringResource(R.string.importance),
                modifier = Modifier.constrainAs(priorityLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(cardView.bottom, margin = 24.dp)
                },
                color = CustomTheme.colors.labelPrimary,
                fontSize = 16.sp
            )

            Box(
                modifier = Modifier
                    .constrainAs(priorityText) {
                        start.linkTo(parent.start)
                        top.linkTo(priorityLabel.bottom, margin = 4.dp)
                    }
                    .background(CustomTheme.colors.backPrimary)
                    .clickable { isPriorityDropdownExpanded = true }
                    .padding(8.dp)
            ) {

                Text(
                    text = when (priority) {
                        ListOfTaskStatus.HIGH -> stringResource(R.string.high)
                        ListOfTaskStatus.NORMAL -> stringResource(R.string.no)
                        ListOfTaskStatus.LOW -> stringResource(R.string.low)
                    }, color = CustomTheme.colors.labelTertiary, fontSize = 14.sp
                )

                DropdownMenu(
                    expanded = isPriorityDropdownExpanded,
                    onDismissRequest = { isPriorityDropdownExpanded = false },
                    modifier = Modifier.background(CustomTheme.colors.backElevated)
                ) {
                    priorityItems.forEach { item ->

                        DropdownMenuItem(
                            onClick = {
                                priority = item
                                taskViewModel.setPriority(item)
                                isPriorityDropdownExpanded = false
                            }
                        ) {

                            Text(
                                text = when (item) {
                                    ListOfTaskStatus.HIGH -> stringResource(R.string.high)
                                    ListOfTaskStatus.NORMAL -> stringResource(R.string.no)
                                    ListOfTaskStatus.LOW -> stringResource(R.string.low)
                                }
                            )
                        }
                    }
                }
            }

            Divider(
                color = CustomTheme.colors.supportSeparator,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .constrainAs(divider) {
                        top.linkTo(priorityText.bottom)
                    }
            )

            Text(
                text = stringResource(R.string.do_before),
                modifier = Modifier.constrainAs(dateLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(divider.bottom, margin = 16.dp)
                },
                color = CustomTheme.colors.labelPrimary,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .constrainAs(dateSwitch) {
                        end.linkTo(parent.end)
                        top.linkTo(dateLabel.top)
                        bottom.linkTo(dateLabel.bottom)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Switch(
                    checked = isDeadlineSet,
                    onCheckedChange = {
                        isDeadlineSet = it
                        if (it) {
                            datePickerDialog.show()
                        } else {
                            deadlineComplete = ""
                            taskViewModel.setDeadline("")
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BlueDark,
                        checkedTrackColor = BlueLight,
                        uncheckedThumbColor = CustomTheme.colors.backElevated,
                        uncheckedTrackColor = CustomTheme.colors.supportOverlay,
                    )
                )
            }

            if (deadlineComplete.isNotEmpty()) {

                Text(
                    text = deadlineComplete,
                    modifier = Modifier.constrainAs(dateSetText) {
                        start.linkTo(parent.start)
                        top.linkTo(dateLabel.bottom, margin = 4.dp)
                    },
                    color = BlueDark,
                    fontSize = 14.sp
                )
            }

            Divider(
                color = CustomTheme.colors.supportSeparator,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp)
                    .constrainAs(divider2) {
                        top.linkTo(dateSwitch.bottom)
                    }
            )

            val deleteColor = if (taskText.isEmpty()) CustomTheme.colors.labelDisable else Color.Red

            IconButton(
                onClick = {
                    taskViewModel.deleteTodoItem()
                    navController.popBackStack()
                },
                modifier = Modifier.constrainAs(deleteButton) {
                    start.linkTo(parent.start)
                    top.linkTo(divider2.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = deleteColor
                )
            }

            Text(
                text = stringResource(R.string.delete),
                color = deleteColor,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(deleteText) {
                    start.linkTo(deleteButton.end, margin = 8.dp)
                    top.linkTo(deleteButton.top)
                    bottom.linkTo(deleteButton.bottom)
                }
            )
        }
    }
}
