package com.example.todolistyandex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.activity.MyApplication
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.theme.Gray
import com.example.todolistyandex.ui.theme.GreenDark
import com.example.todolistyandex.ui.theme.RedDark
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModelFactory


class ListOfTaskFragment : Fragment() {

    private val repository by lazy { (requireActivity().application as MyApplication).repository }
    private val taskViewModel: ListOfTaskViewModel by viewModels {
        ListOfTaskViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                ListOfTaskScreen(taskViewModel = taskViewModel, navController = navController)
            }
        }
    }

}

@Composable
fun ListOfTaskScreen(
    taskViewModel: ListOfTaskViewModel = viewModel(),
    navController: NavController
) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    val showCompletedTasks by taskViewModel.showCompletedTasks.collectAsState()
    val completedTaskCount by taskViewModel.completedTaskCount.collectAsState(0)
    val scrollState = rememberLazyListState()

    Scaffold(
        floatingActionButton = {

            FloatingActionButton(
                onClick = { navController.navigate("createNewTask/-1/ /NORMAL") },
                modifier = Modifier.padding(10.dp),
                containerColor = CustomTheme.colors.colorBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(50)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(id = R.string.add_task)
                )
            }
        },
        containerColor = CustomTheme.colors.backPrimary
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val textSize by remember {
                derivedStateOf {
                    if (scrollState.firstVisibleItemScrollOffset > 0) 20.sp else 32.sp
                }
            }
            val showAdditionalText by remember {
                derivedStateOf {
                    scrollState.firstVisibleItemScrollOffset == 0
                }
            }

            Text(
                text = stringResource(id = R.string.my_business),
                color = CustomTheme.colors.labelPrimary,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = textSize,
                modifier = Modifier.padding(start = 60.dp, top = 82.dp)
            )
            if (showAdditionalText) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(id = R.string.done, completedTaskCount),
                        color = CustomTheme.colors.labelSecondary,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(

                        onClick = { taskViewModel.toggleShowCompletedTasks() },
                    ) {

                        Icon(
                            painter = painterResource(
                                id = if (showCompletedTasks) R.drawable.cell_eye else R.drawable.cell_eye_off
                            ),
                            contentDescription = stringResource(id = R.string.toggle_completed_tasks),
                            tint = CustomTheme.colors.colorBlue
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 18.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = CustomTheme.colors.backSecondary
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        state = scrollState
                    ) {
                        items(tasks) { task ->
                            TaskItem(task, onClick = {
                                navController.navigate("createNewTask/${task.id}/${task.text}/${task.priority}")
                            }, taskViewModel = taskViewModel, navController = navController)
                        }
                    }

                    TextButton(
                        onClick = { navController.navigate("createNewTask/-1/ /NORMAL") },
                        modifier = Modifier.padding(start = 64.dp, top = 16.dp, bottom = 16.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.new_task),
                            color = CustomTheme.colors.labelSecondary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: ToDoItem,
    onClick: () -> Unit,
    taskViewModel: ListOfTaskViewModel,
    navController: NavController
) {
    val checkboxColor = if (task.completeFlag) GreenDark else when (task.priority) {
        ListOfTaskStatus.HIGH -> RedDark
        else -> Gray
    }

    val priorityIcon = when (task.priority) {
        ListOfTaskStatus.HIGH -> R.drawable.high_prioritet
        ListOfTaskStatus.LOW -> R.drawable.low_prioritet
        else -> null
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = task.completeFlag,
            onCheckedChange = {
                taskViewModel.updateTaskCompletion(task.id, it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = checkboxColor,
                uncheckedColor = checkboxColor
            )
        )

        if (priorityIcon != null && !task.completeFlag) {
            Icon(
                painter = painterResource(id = priorityIcon),
                contentDescription = stringResource(id = R.string.high_priority),
                tint = if (task.priority == ListOfTaskStatus.HIGH) Color.Red else Color.Unspecified,
                modifier = Modifier.padding(end = 3.dp)
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {

            Column {

                Text(
                    text = task.text,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = if (task.completeFlag) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    color = if (task.completeFlag) Color.Gray else CustomTheme.colors.labelPrimary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                if (!task.deadlineComplete.isNullOrEmpty()) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = stringResource(id = R.string.calendar),
                            tint = CustomTheme.colors.labelSecondary,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Дата: ${task.deadlineComplete}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = {
                navController.navigate("createNewTask/${task.id}/${task.text}/${task.priority}")
            }
        ) {

            Icon(
                painter = painterResource(id = R.drawable.icons),
                contentDescription = stringResource(id = R.string.info)
            )
        }
    }
}














