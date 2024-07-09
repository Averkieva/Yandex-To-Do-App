package com.example.todolistyandex.ui.compose.createnewtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.BlueDark
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel

@Composable
fun TaskHeader(navController: NavController, taskText: String, taskViewModel: TaskViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (closeButton, saveButton) = createRefs()

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.constrainAs(closeButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = CustomTheme.colors.labelPrimary)
        }

        TextButton(
            onClick = {
                if (taskText.isNotEmpty()) {
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
    }
}